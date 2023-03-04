package software.sirsch.sa4e.puzzlesWebapp;

import javax.annotation.Nonnull;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;

/**
 * Diese Klasse stellt eine vereinfachte Schnittstelle für den Zugriff auf Mqtt bereit.
 *
 * <p>
 *     Die Implementierung basiert auf Eclipse Paho.
 * </p>
 *
 * @author sirsch
 * @since 04.03.2023
 */
public class Client {

	/**
	 * Dieses Feld muss den MQTT-Client enthalten.
	 */
	@Nonnull
	private final MqttClient mqttClient;

	/**
	 * Dieses Feld muss das Topic enthalten.
	 */
	@Nonnull
	private final String topic;

	/**
	 * Dieser Konstruktor verbindet den Client.
	 *
	 * @param settings die zu verwendenden Einstellungen
	 * @param clientListener der zu verwendende {@link ClientListener}
	 */
	public Client(@Nonnull final Settings settings, @Nonnull final ClientListener clientListener) {
		this(settings, clientListener, MqttClient::new, ClientCallbackAdapter::new);
	}

	/**
	 * Dieser Konstruktor erlaubt das Einschleusen von Objekten zum Testen.
	 *
	 * @param settings die zu verwendenden Einstellungen
	 * @param clientListener der zu verwendende {@link ClientListener}
	 * @param mqttClientFactory die zu verwendende {@link MqttClientFactory}
	 * @param clientCallbackAdapterFactory die zu verwendende {@link ClientCallbackAdapterFactory}
	 */
	protected Client(
			@Nonnull final Settings settings,
			@Nonnull final ClientListener clientListener,
			@Nonnull final MqttClientFactory mqttClientFactory,
			@Nonnull final ClientCallbackAdapterFactory clientCallbackAdapterFactory) {

		try {
			this.mqttClient = mqttClientFactory.create(
					settings.requireBrokerUrl(),
					settings.getClientId(),
					new MemoryPersistence());
			this.mqttClient.setCallback(clientCallbackAdapterFactory.create(clientListener));
			this.mqttClient.connect();
			this.mqttClient.subscribe(settings.requireTopic(), settings.getQos());
			this.topic = settings.requireTopic();
		} catch (MqttException e) {
			throw new ClientException(e);
		}
	}

	/**
	 * Diese Methode trennt die Verbindung.
	 */
	public void disconnect() {
		try {
			this.mqttClient.unsubscribe(this.topic);
			this.mqttClient.disconnect();
		} catch (MqttException e) {
			throw new ClientException(e);
		}
	}
}
