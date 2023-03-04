package software.sirsch.sa4e.puzzlesWebapp;

import javax.annotation.Nonnull;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttClientPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;

/**
 * Diese Schnittstelle beschreibt die Fabrikmethode für {@link MqttClient}.
 *
 * @author sirsch
 * @since 04.03.2023
 */
@FunctionalInterface
public interface MqttClientFactory {

	/**
	 * Diese Methode erzeugt einen neuen {@link MqttClient}.
	 *
	 * @param serverURI die zu verwendende Server-URI
	 * @param clientId die zu verwendende Client-ID
	 * @param persistence die zu verwendende {@link MqttClientPersistence}
	 * @return die erzeugte Instanz
	 * @throws MqttException zeigt ein Problem an
	 */
	@Nonnull
	MqttClient create(
			@Nonnull String serverURI,
			@Nonnull String clientId,
			@Nonnull MqttClientPersistence persistence) throws MqttException;
}
