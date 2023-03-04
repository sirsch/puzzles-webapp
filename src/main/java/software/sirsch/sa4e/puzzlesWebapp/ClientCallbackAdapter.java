package software.sirsch.sa4e.puzzlesWebapp;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Diese Klasse stellt einen Adapter von {@link MqttCallback} auf {@link ClientListener} bereit.
 *
 * @author sirsch
 * @since 04.03.2023
 */
public class ClientCallbackAdapter implements MqttCallback {

	/**
	 * Dieses Feld muss den zu verwendenden {@link ClientListener} enthalten.
	 */
	@Nonnull
	private final ClientListener clientListener;

	/**
	 * Dieser Konstruktor legt den zu verwendenden {@link ClientListener} fest.
	 *
	 * @param clientListener der zu setzende {@link ClientListener}
	 */
	protected ClientCallbackAdapter(@Nonnull final ClientListener clientListener) {
		this.clientListener = clientListener;
	}

	@Override
	public void disconnected(@Nonnull final MqttDisconnectResponse disconnectResponse) {
		this.clientListener.onNotification("Die Verbindung wurde getrennt.");
	}

	@Override
	public void mqttErrorOccurred(@Nonnull final MqttException exception) {
		this.clientListener.onNotification(
				"Es ist ein Fehler aufgetreten! " + exception.getMessage());
	}

	@Override
	public void messageArrived(
			@CheckForNull final String topic,
			@Nonnull final MqttMessage message) {

		this.clientListener.onMessage(new String(message.getPayload(), UTF_8));
	}

	@Override
	public void deliveryComplete(@CheckForNull final IMqttToken token) {
	}

	@Override
	public void connectComplete(final boolean reconnect, @CheckForNull final String serverURI) {
		this.clientListener.onNotification("Verbunden mit " + serverURI + ".");
	}

	@Override
	public void authPacketArrived(
			final int reasonCode,
			@CheckForNull final MqttProperties properties) {
	}
}
