package software.sirsch.sa4e.puzzlesWebapp;

import java.util.Optional;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

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
		this(new Settings(), Client::new);
	}

	/**
	 * Dieser Konstruktor erlaubt das Einschleusen von Objekten zum Testen.
	 *
	 * @param settings die zu setzenden Einstellungen
	 * @param clientFactory die zu setzende Fabrik für Clients
	 */
	protected PuzzlesPresenter(
			@Nonnull final Settings settings,
			@Nonnull final ClientFactory clientFactory) {

		this.settings = settings;
		this.clientFactory = clientFactory;
	}

	/**
	 * Diese Methode initialisiert diese Instanz mit der zu verwaltenden View.
	 *
	 * @param puzzlesView die zu setzende View
	 */
	public void init(@Nonnull final PuzzlesView puzzlesView) {
		this.view = puzzlesView;
		this.view.readSettings(this.settings);
	}

	/**
	 * Diese Methode behandelt den Click auf den Verbinden-Button.
	 */
	public void onConnect() {
		this.onDisconnect();
		this.connect();
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
		if (this.client != null) {
			this.runWithErrorHandler(this.client::disconnect);
			this.client = null;
		}
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
