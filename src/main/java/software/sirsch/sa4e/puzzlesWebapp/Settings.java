package software.sirsch.sa4e.puzzlesWebapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

/**
 * Diese Klasse enthält die zu verwendende Konfiguration für MQTT.
 *
 * @author sirsch
 * @since 04.03.2023
 */
public class Settings {

	/**
	 * Dieses Feld enthält den Vorgabewert für QOS, der verwendet werden soll, wenn keine
	 * Konfiguration vorgenommen wurde.
	 */
	@Nonnull
	private static final Integer DEFAULT_QOS = 2;

	/**
	 * Dieses Feld enthält die Client-ID die verwendet werden soll, falls es keine gesonderte
	 * Konfiguration vorgenommen wurde.
	 */
	@Nonnull
	private final String defaultClientId;

	/**
	 * Dieses Feld kann die Broker-URL enthalten.
	 */
	@CheckForNull
	private String brokerUrl;

	/**
	 * Dieses Feld kann die Client-ID enthalten.
	 */
	@CheckForNull
	private String clientId;

	/**
	 * Dieses Feld kann den Benutzernamen enthalten.
	 */
	@CheckForNull
	private String username;

	/**
	 * Dieses Feld kann das Passwort enthalten.
	 */
	@CheckForNull
	private String password;

	/**
	 * Dieses Feld enthält die Liste der Topics.
	 */
	@Nonnull
	private List<String> topics = new ArrayList<>();

	/**
	 * Dieses Feld kann das QOS enthalten.
	 */
	@CheckForNull
	private Integer qos;

	/**
	 * Dieser Konstruktor nimmt die interne Initialisierung vor.
	 */
	public Settings() {
		final int randomStringLength = 8;

		this.brokerUrl = "tcp://localhost:1883";
		this.defaultClientId = "puzzles-webapp_"
				+ randomAlphanumeric(randomStringLength).toLowerCase(Locale.US);
		this.topics.add("Zahlenraetsel");
		this.topics.add("Loesung");
	}

	/**
	 * Diese Methode gibt die Broker-URL zurück.
	 *
	 * @return die URL, falls vorhanden
	 */
	@CheckForNull
	public String getBrokerUrl() {
		return this.brokerUrl;
	}

	/**
	 * Diese Methode legt die Broker-URL fest.
	 *
	 * @param brokerUrl die zu setzende URL
	 */
	public void setBrokerUrl(@CheckForNull final String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	/**
	 * Diese Methode gibt die Broker-URL zurück und zeigt ein Fehlen per Ausnahme an.
	 *
	 * @return die Broker-URL, nicht {@code null}
	 */
	@Nonnull
	public String requireBrokerUrl() {
		return Optional.ofNullable(this.brokerUrl)
				.orElseThrow(this::createMissingBrokerUrlException);
	}

	/**
	 * Diese Methode erzeugt die Ausnahme, die anzeigt, dass die Broker-URL nicht vorhanden ist.
	 *
	 * @return die erzeugte Ausnahme
	 */
	@Nonnull
	private IllegalStateException createMissingBrokerUrlException() {
		return new IllegalStateException("Missing Broker-URL!");
	}

	/**
	 * Diese Methode gibt die Client-ID zurück.
	 *
	 * @return die ID
	 */
	@Nonnull
	public String getClientId() {
		return Optional.ofNullable(this.clientId)
				.orElse(this.defaultClientId);
	}

	/**
	 * Diese Methode legt die Client-ID fest.
	 *
	 * @param clientId die zu setzende ID
	 */
	public void setClientId(@CheckForNull final String clientId) {
		this.clientId = clientId;
	}

	/**
	 * Diese Methode gibt den Benutzernamen zurück.
	 *
	 * @return der Benutzername, falls vorhanden
	 */
	@CheckForNull
	public String getUsername() {
		return this.username;
	}

	/**
	 * Diese Methode legt den Benutzernamen fest.
	 *
	 * @param username der zu setzende Benutzername
	 */
	public void setUsername(@CheckForNull final String username) {
		this.username = username;
	}

	/**
	 * Diese Methode gibt das Passwort zurück.
	 *
	 * @return das Passwort, falls vorhanden
	 */
	@CheckForNull
	public String getPassword() {
		return this.password;
	}

	/**
	 * Diese Methode legt das Passwort fest.
	 *
	 * @param password das zu setzende Passwort
	 */
	public void setPassword(@CheckForNull final String password) {
		this.password = password;
	}

	/**
	 * Diese Methode gibt die Liste der Topics zurück.
	 *
	 * @return die Liste der Topics
	 */
	@Nonnull
	public List<String> getTopics() {
		return List.copyOf(this.topics);
	}

	/**
	 * Diese Methode legt die Liste der Topics fest.
	 *
	 * @param topics die zu setzende Liste
	 */
	public void setTopics(@Nonnull final List<String> topics) {
		this.topics.clear();
		this.topics.addAll(topics);
	}

	/**
	 * Diese Methode gibt QOS zurück.
	 *
	 * @return das QOS, falls vorhanden
	 */
	@Nonnull
	public Integer getQos() {
		return Optional.ofNullable(this.qos)
				.orElse(DEFAULT_QOS);
	}

	/**
	 * Diese Methode legt das QOS fest.
	 *
	 * @param qos das zu setzende QOS
	 */
	public void setQos(@CheckForNull final Integer qos) {
		this.qos = qos;
	}
}
