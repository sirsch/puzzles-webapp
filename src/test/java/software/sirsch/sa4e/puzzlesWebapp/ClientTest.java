package software.sirsch.sa4e.puzzlesWebapp;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Diese Klasse stellt Tests für {@link Client} bereit.
 *
 * @author sirsch
 * @since 04.03.2023
 */
public class ClientTest {

	/**
	 * Dieses Feld soll den Mock für {@link Settings} enthalten.
	 */
	private Settings settings;

	/**
	 * Dieses Feld soll den Mock für {@link ClientListener} enthalten.
	 */
	private ClientListener clientListener;

	/**
	 * Dieses Feld soll den Mock für {@link MqttClient} enthalten.
	 */
	private MqttClient mqttClient;

	/**
	 * Dieses Feld soll den Mock für {@link MqttClientFactory} enthalten.
	 */
	private MqttClientFactory mqttClientFactory;

	/**
	 * Dieses Feld soll den Mock für {@link ClientCallbackAdapter} enthalten.
	 */
	private ClientCallbackAdapter clientCallbackAdapter;

	/**
	 * Dieses Feld soll den Mock für {@link ClientCallbackAdapterFactory} enthalten.
	 */
	private ClientCallbackAdapterFactory clientCallbackAdapterFactory;

	/**
	 * Dieses Feld soll das zu testende Objekt enthalten.
	 */
	private Client objectUnderTest;

	/**
	 * Diese Methode bereitet die Testumgebung für jeden Testfall vor.
	 *
	 * @throws MqttException wird in diesem Testfall nicht erwartet
	 */
	@BeforeEach
	public void setUp() throws MqttException {
		InOrder orderVerifier;

		this.settings = mock(Settings.class);
		this.clientListener = mock(ClientListener.class);
		this.mqttClient = mock(MqttClient.class);
		this.mqttClientFactory = mock(MqttClientFactory.class);
		this.clientCallbackAdapter = mock(ClientCallbackAdapter.class);
		this.clientCallbackAdapterFactory = mock(ClientCallbackAdapterFactory.class);
		orderVerifier = inOrder(this.mqttClient);

		when(this.settings.requireBrokerUrl()).thenReturn("test://broker/url");
		when(this.settings.getClientId()).thenReturn("testClientId");
		when(this.settings.requireTopic()).thenReturn("testTopic");
		when(this.settings.getQos()).thenReturn(2);
		when(this.mqttClientFactory.create(
				eq("test://broker/url"),
				eq("testClientId"),
				any(MemoryPersistence.class))).thenReturn(this.mqttClient);
		when(this.clientCallbackAdapterFactory.create(this.clientListener))
				.thenReturn(this.clientCallbackAdapter);

		this.objectUnderTest = new Client(
				this.settings,
				this.clientListener,
				this.mqttClientFactory,
				this.clientCallbackAdapterFactory);

		orderVerifier.verify(this.mqttClient).setCallback(this.clientCallbackAdapter);
		orderVerifier.verify(this.mqttClient).connect();
		orderVerifier.verify(this.mqttClient).subscribe("testTopic", 2);
	}

	/**
	 * Diese Methode prüft die Übersetzung von Ausnahmen bei
	 * {@link Client#Client(Settings, ClientListener, MqttClientFactory,
	 * ClientCallbackAdapterFactory)}.
	 *
	 * @throws MqttException wird in diesem Testfall nicht erwartet
	 */
	@Test
	public void testConstructorExceptionTranslation() throws MqttException {
		MqttException mqttException = new MqttException(new RuntimeException("testException"));
		ClientException caughtException;

		doThrow(mqttException).when(this.mqttClient).connect();

		caughtException = assertThrows(
				ClientException.class,
				() -> new Client(
						this.settings,
						this.clientListener,
						this.mqttClientFactory,
						this.clientCallbackAdapterFactory));

		assertEquals(mqttException, caughtException.getCause());
	}

	/**
	 * Diese Methode prüft {@link Client#disconnect()}.
	 *
	 * @throws MqttException wird in diesem Testfall nicht erwartet
	 */
	@Test
	public void testDisconnect() throws MqttException {
		this.objectUnderTest.disconnect();

		verify(this.mqttClient).unsubscribe("testTopic");
		verify(this.mqttClient).disconnect();
	}

	/**
	 * Diese Methode prüft die Übersetzung von Ausnahmen bei {@link Client#disconnect()}.
	 *
	 * @throws MqttException wird in diesem Testfall nicht erwartet
	 */
	@Test
	public void testDisconnectExceptionTranslation() throws MqttException {
		MqttException mqttException = new MqttException(new RuntimeException("testException"));
		ClientException caughtException;

		doThrow(mqttException).when(this.mqttClient).disconnect();

		caughtException = assertThrows(
				ClientException.class,
				() -> this.objectUnderTest.disconnect());

		assertEquals(mqttException, caughtException.getCause());
	}
}