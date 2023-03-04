package software.sirsch.sa4e.puzzlesWebapp;

import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Diese Klasse stellt Tests für {@link ClientCallbackAdapter} bereit.
 *
 * @author sirsch
 * @since 04.03.2023
 */
public class ClientCallbackAdapterTest {

	/**
	 * Dieses Feld soll den Mock für {@link ClientListener} enthalten.
	 */
	private ClientListener clientListener;

	/**
	 * Dieses Feld soll das zu testende Objekt enthalten.
	 */
	private ClientCallbackAdapter objectUnderTest;

	/**
	 * Diese Methode bereitet die Testumgebung für jeden Testfall vor.
	 */
	@BeforeEach
	public void setUp() {
		this.clientListener = mock(ClientListener.class);

		this.objectUnderTest = new ClientCallbackAdapter(this.clientListener);
	}

	/**
	 * Diese Methode prüft {@link ClientCallbackAdapter#disconnected(MqttDisconnectResponse)}.
	 */
	@Test
	public void testDisconnected() {
		this.objectUnderTest.disconnected(mock(MqttDisconnectResponse.class));

		verify(this.clientListener).onNotification("Die Verbindung wurde getrennt.");
	}

	/**
	 * Diese Methode prüft {@link ClientCallbackAdapter#mqttErrorOccurred(MqttException)}.
	 */
	@Test
	public void testMqttErrorOccurred() {
		MqttException mqttException = mock(MqttException.class);

		when(mqttException.getMessage()).thenReturn("testException");

		this.objectUnderTest.mqttErrorOccurred(mqttException);

		verify(this.clientListener).onNotification("Es ist ein Fehler aufgetreten! testException");
	}

	/**
	 * Diese Methode prüft {@link ClientCallbackAdapter#messageArrived(String, MqttMessage)}.
	 */
	@Test
	public void testMessageArrived() {
		MqttMessage mqttMessage = mock(MqttMessage.class);

		when(mqttMessage.getPayload()).thenReturn("testPayload".getBytes(UTF_8));

		this.objectUnderTest.messageArrived("testTopic", mqttMessage);

		verify(this.clientListener).onMessage("testPayload");
	}

	/**
	 * Diese Methode prüft {@link ClientCallbackAdapter#deliveryComplete(IMqttToken)}.
	 */
	@Test
	public void testDeliveryComplete() {
		assertDoesNotThrow(() -> this.objectUnderTest.deliveryComplete(mock(IMqttToken.class)));

		verifyNoInteractions(this.clientListener);
	}

	/**
	 * Diese Methode prüft {@link ClientCallbackAdapter#connectComplete(boolean, String)}.
	 */
	@Test
	public void testConnectComplete() {
		this.objectUnderTest.connectComplete(false, "test://server/uri");

		verify(this.clientListener).onNotification("Verbunden mit test://server/uri.");
	}

	/**
	 * Diese Methode prüft {@link ClientCallbackAdapter#authPacketArrived(int, MqttProperties)}.
	 */
	@Test
	public void testAuthPacketArrived() {
		this.objectUnderTest.authPacketArrived(42, mock(MqttProperties.class));

		verifyNoInteractions(this.clientListener);
	}
}
