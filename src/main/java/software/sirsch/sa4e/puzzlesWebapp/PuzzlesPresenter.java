package software.sirsch.sa4e.puzzlesWebapp;

import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.stereotype.Component;

/**
 * Diese Klasse stellt einen Presenter für {@link PuzzlesView} bereit.
 *
 * @author sirsch
 * @since 05.03.2023
 */
@Component
@UIScope
public class PuzzlesPresenter implements ClientListener {

	/**
	 * Dieses Feld muss die zu verwendenden Einstellungen enthalten.
	 */
	@Nonnull
	private final Settings settings;

	/**
	 * Dieses Feld muss die {@link ClientFactory} enthalten.
	 */
	@Nonnull
	private final ClientFactory clientFactory;

	/**
	 * Dieses Feld muss den {@link ObjectMapper} zum Parsen von JSON enthalten.
	 */
	@Nonnull
	private final ObjectMapper objectMapper;

	/**
	 * Dieses Feld soll die {@link PuzzlesView} enthalten, für die dieser Presenter zuständig ist.
	 */
	@CheckForNull
	private PuzzlesView view;

	/**
	 * Dieses Feld enthält den {@link Client}, solange eine Verbindung besteht.
	 */
	@CheckForNull
	private Client client;

	/**
	 * Dieser Konstruktor nimmt die interne Initialisierung vor.
	 */
	public PuzzlesPresenter() {
		this(new Settings(), Client::new, new ObjectMapper());
	}

	/**
	 * Dieser Konstruktor erlaubt das Einschleusen von Objekten zum Testen.
	 *
	 * @param settings die zu setzenden Einstellungen
	 * @param clientFactory die zu setzende Fabrik für Clients
	 * @param objectMapper der zu setzende {@link ObjectMapper}
	 */
	protected PuzzlesPresenter(
			@Nonnull final Settings settings,
			@Nonnull final ClientFactory clientFactory,
			@Nonnull final ObjectMapper objectMapper) {

		this.settings = settings;
		this.clientFactory = clientFactory;
		this.objectMapper = objectMapper;
	}

	/**
	 * Diese Methode initialisiert diese Instanz mit der zu verwaltenden View.
	 *
	 * @param puzzlesView die zu setzende View
	 */
	public void init(@Nonnull final PuzzlesView puzzlesView) {
		this.view = puzzlesView;
		this.view.readSettings(this.settings);
		this.updateViewConnected();
	}

	/**
	 * Diese Methode behandelt den Click auf den Verbinden-Button.
	 */
	public void onConnect() {
		this.disconnect();
		this.connect();
		this.updateViewConnected();
	}

	/**
	 * Diese Methode stellt die Verbindung mittels des {@link Client} her, sofern die Einstellungen
	 * aus der {@link #view} ausgelesen werden können.
	 */
	private void connect() {
		this.requireView().writeSettings(this.settings)
				.ifPresent(this::connect);
	}

	/**
	 * Diese Methode stellt die Verbindung mittels des {@link Client} her.
	 *
	 * @param settingsToUse die zu verwendenden Einstellungen
	 */
	private void connect(@Nonnull final Settings settingsToUse) {
		this.runWithErrorHandler(
				() -> this.client = this.clientFactory.create(settingsToUse, this));
	}

	/**
	 * Diese Methode behandelt den Click auf den Trennen-Button.
	 */
	public void onDisconnect() {
		this.disconnect();
		this.updateViewConnected();
	}

	/**
	 * Diese Methode trennt eine Verbindung, falls vorhanden.
	 */
	private void disconnect() {
		if (this.client != null) {
			this.runWithErrorHandler(this.client::disconnect);
			this.client = null;
		}
	}

	/**
	 * Diese Methode aktualisiert die Visualisierung des Verbindungszustandes.
	 */
	private void updateViewConnected() {
		this.requireView().setConnected(this.client != null);
	}

	/**
	 * Diese Methode führt ein Kommando aus und meldet eventuelle Fehler an
	 * {@link #onNotification(String)}.
	 *
	 * @param command das auszuführende Kommando
	 */
	private void runWithErrorHandler(@Nonnull final Runnable command) {
		try {
			command.run();
		} catch (ClientException e) {
			this.onNotification(e.getMessage());
		}
	}

	@Override
	public void onMessage(@Nonnull final String message) {
		if (this.isRequestMessage(message)) {
			this.handleRequestMessage(message);
		} else if (this.isResponseMessage(message)) {
			this.handleResponseMessage(message);
		} else {
			this.requireView().addNotification(
					"Es wurde eine Nachricht eines unbekannten Typs empfangen!");
		}
	}

	/**
	 * Diese Methode prüft, ob es sich um eine Anfrage handelt.
	 *
	 * @param message die zu prüfende Nachricht
	 * @return {@code true}, falls die Nachricht den erwarteten Typ aufweist
	 */
	private boolean isRequestMessage(@Nonnull final String message) {
		return this.parseRequest(message)
				.isPresent();
	}

	/**
	 * Diese Methode behandelt eine Anfrage.
	 *
	 * @param message die zu behandelnde Nachricht
	 */
	private void handleRequestMessage(@Nonnull final String message) {
		this.parseRequest(message)
				.ifPresent(this.requireView()::addRequest);
	}

	/**
	 * Diese Methode versucht eine Anfrage zu parsen.
	 *
	 * @param message die zu untersuchende Nachricht
	 * @return die optional ermittelte Anfrage
	 */
	@Nonnull
	private Optional<CommonSolvePuzzleRequest> parseRequest(@Nonnull final String message) {
		return this.parse(
				message,
				CommonSolvePuzzleRequest.class,
				CommonSolvePuzzleRequest::validate);
	}

	/**
	 * Diese Methode prüft, ob es sich um eine Antwort handelt.
	 *
	 * @param message die zu prüfende Nachricht
	 * @return {@code true}, falls die Nachricht den erwarteten Typ aufweist
	 */
	private boolean isResponseMessage(@Nonnull final String message) {
		return this.parseResponse(message)
				.isPresent();
	}

	/**
	 * Diese Methode behandelt eine Antwort.
	 *
	 * @param message die zu behandelnde Nachricht
	 */
	private void handleResponseMessage(@Nonnull final String message) {
		this.parseResponse(message)
				.ifPresent(this.requireView()::addResponse);
	}

	/**
	 * Diese Methode versucht eine Antwort zu parsen.
	 *
	 * @param message die zu untersuchende Nachricht
	 * @return die optional ermittelte Antwort
	 */
	@Nonnull
	private Optional<CommonSolvePuzzleResponse> parseResponse(@Nonnull final String message) {
		return this.parse(
				message,
				CommonSolvePuzzleResponse.class,
				CommonSolvePuzzleResponse::validate);
	}

	/**
	 * Diese Methode versucht eine Nachricht eines bestimmten Typs zu parsen und validiert diese.
	 *
	 * @param message die zu untersuchende Nachricht
	 * @param type die Klasse des Nachrichten-Typs
	 * @param validator die zu verwendende Validierungsmethode
	 * @return die geparste Nachricht, falls sie geparst und validiert werden konnte
	 * @param <T> der erwartete Nachrichten-Typ
	 */
	@Nonnull
	private <T> Optional<T> parse(
			@Nonnull final String message,
			@Nonnull final Class<T> type,
			@Nonnull final Predicate<T> validator) {

		try {
			return Optional.ofNullable(this.objectMapper.readValue(message, type))
					.filter(validator);
		} catch (JsonProcessingException e) {
			return Optional.empty();
		}
	}

	@Override
	public void onNotification(@Nonnull final String notification) {
		this.requireView().addNotification(notification);
	}

	/**
	 * Diese Methode gibt die View zurück, unter der Annahme, dass die View vorhanden ist.
	 *
	 * @return die View
	 * @throws IllegalStateException falls die View nicht gesetzt ist
	 */
	@Nonnull
	private PuzzlesView requireView() {
		return Optional.ofNullable(this.view)
				.orElseThrow(IllegalStateException::new);
	}
}
