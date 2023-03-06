package software.sirsch.sa4e.puzzlesWebapp;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Diese Klasse stellt die Benutzeroberfläche für die puzzles-webapp bereit.
 *
 * @author sirsch
 * @since 04.03.2023
 */
@Route("")
public class PuzzlesView extends VerticalLayout {

	/**
	 * Dieses Feld muss das {@link TextField} für die Broker-URL enthalten.
	 */
	@Nonnull
	private final TextField brokerUrlField = new TextField("MQTT Server URI");

	/**
	 * Dieses Feld muss das {@link TextField} für die Client-ID enthalten.
	 */
	@Nonnull
	private final TextField clientIdTextField = new TextField("Client ID");

	/**
	 * Dieses Feld muss das {@link TextField} für den Benutzernamen enthalten.
	 */
	@Nonnull
	private final TextField usernameTextField = new TextField("Benutzername");

	/**
	 * Dieses Feld muss das {@link PasswordField} enthalten.
	 */
	@Nonnull
	private final PasswordField passwordField = new PasswordField("Passwort");

	/**
	 * Dieses Feld muss das {@link TextField} für die Topics enthalten.
	 */
	@Nonnull
	private final TextField topicsTextField = new TextField("Topics");

	/**
	 * Dieses Feld muss das {@link TextField} für QOS enthalten.
	 */
	@Nonnull
	private final TextField qosTextField = new TextField("QOS");

	/**
	 * Dieses Feld muss den {@link Button} zum Verbinden enthalten.
	 */
	@Nonnull
	private final Button connectButton = new Button("verbinden");

	/**
	 * Dieses Feld muss den {@link Button} zum Trennen enthalten.
	 */
	@Nonnull
	private final Button disconnectButton = new Button("trennen");

	/**
	 * Dieses Feld muss den {@link Binder} für die Formularfelder enthalten.
	 */
	@Nonnull
	private final Binder<Settings> settingsBinder = new Binder<>(Settings.class);

	/**
	 * Dieses Feld muss das Layout für die Ausgabe enthalten.
	 */
	@Nonnull
	private final VerticalLayout outputLayout = new VerticalLayout();

	/**
	 * Dieses Feld muss den {@link PuzzlesPresenter} enthalten.
	 */
	@Nonnull
	private final PuzzlesPresenter presenter;

	/**
	 * Dieses Feld muss den {@link Consumer} enthalten, der Meldungen auf der UI einblendet.
	 */
	@Nonnull
	private final Consumer<String> notificator;

	/**
	 * Dieses Feld muss den Provider enthalten, der die aktuelle UI einer {@link Component}
	 * ermitteln kann.
	 */
	@Nonnull
	private final Function<Component, Optional<UI>> uiProvider;

	/**
	 * Dieser Konstruktor nimmt die interne Initialisierung vor.
	 *
	 * @param presenter der zu setzende Presenter
	 */
	@Autowired
	public PuzzlesView(@Nonnull final PuzzlesPresenter presenter) {
		this(presenter, Notification::show, Component::getUI);
	}

	/**
	 * Dieser Konstruktor nimmt die interne Initialisierung vor.
	 *
	 * @param presenter der zu setzende Presenter
	 * @param notificator der zu setzende Notificator
	 * @param uiProvider der zu setzende UI-Provider
	 */
	protected PuzzlesView(
			@Nonnull final PuzzlesPresenter presenter,
			@Nonnull final Consumer<String> notificator,
			@Nonnull final Function<Component, Optional<UI>> uiProvider) {

		this.presenter = presenter;
		this.notificator = notificator;
		this.uiProvider = uiProvider;

		this.initializeBinder();
		this.createLayout();
	}

	/**
	 * Diese Methode initialisiert den {@link #settingsBinder}.
	 */
	private void initializeBinder() {
		this.settingsBinder.forField(this.brokerUrlField)
				.asRequired("Die Broker URI muss angegeben werden!")
				.bind(Settings::getBrokerUrl, Settings::setBrokerUrl);
		this.settingsBinder.forField(this.clientIdTextField)
				.asRequired("Die Client ID muss angegeben werden!")
				.bind(Settings::getClientId, Settings::setClientId);
		this.settingsBinder.forField(this.usernameTextField)
				.bind(Settings::getUsername, Settings::setUsername);
		this.settingsBinder.forField(this.passwordField)
				.bind(Settings::getPassword, Settings::setPassword);
		this.settingsBinder.forField(this.topicsTextField)
				.withConverter(new StringToListConverter())
				.bind(Settings::getTopics, Settings::setTopics);
		this.settingsBinder.forField(this.qosTextField)
				.withConverter(new StringToIntegerConverter(
						"Hier muss eine Zahl eingegeben werden!"))
				.bind(Settings::getQos, Settings::setQos);
	}

	/**
	 * Diese Methode stellt das Layout dieser Komponente her.
	 */
	private void createLayout() {
		this.add(this.createSettingsHeading());
		this.add(this.createSettingsForm());
		this.add(this.createSettingsButtons());
		this.add(new Hr());
		this.add(this.outputLayout);
	}

	/**
	 * Diese Methode erzeugt die Überschrift für das Formular.
	 *
	 * @return die Überschrift
	 */
	@Nonnull
	private Component createSettingsHeading() {
		H3 heading = new H3("Zugangsdaten");

		return heading;
	}

	/**
	 * Diese Methode erzeugt das Layout für das Formular.
	 *
	 * @return das erzeugte Formular
	 */
	private Component createSettingsForm() {
		FormLayout formLayout = new FormLayout(
				this.brokerUrlField,
				this.clientIdTextField,
				this.usernameTextField,
				this.passwordField,
				this.topicsTextField,
				this.qosTextField);

		formLayout.setResponsiveSteps(
				new ResponsiveStep("0", 1),
				new ResponsiveStep("500px", 2),
				new ResponsiveStep("1200px", 2 * 2));
		return formLayout;
	}

	/**
	 * Diese Methode erzeugt das Layout für die Buttons des Formulars.
	 *
	 * @return das erzeugte Button-Layout
	 */
	@Nonnull
	private Component createSettingsButtons() {
		HorizontalLayout buttonLayout = new HorizontalLayout();

		this.connectButton.addClickListener(event -> this.presenter.onConnect());
		this.connectButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		buttonLayout.add(this.connectButton);
		this.disconnectButton.addClickListener(event -> this.presenter.onDisconnect());
		buttonLayout.add(this.disconnectButton);
		return buttonLayout;
	}

	/**
	 * Diese Methode führt die Initialisierung durch.
	 */
	@PostConstruct
	public void init() {
		this.presenter.init(this);
	}

	/**
	 * Diese Methode setzt die Einstellungen.
	 *
	 * @param settings die zu setzende Einstellungen
	 */
	public void readSettings(@Nonnull final Settings settings) {
		this.settingsBinder.readBean(settings);
	}

	/**
	 * Diese Methode liest die Einstellungen aus.
	 *
	 * @param settings die zu befüllende Instanz
	 * @return die befüllte Instanz oder {@code null}, falls das Auslesen nicht möglich war
	 */
	@Nonnull
	public Optional<Settings> writeSettings(@Nonnull final Settings settings) {
		try {
			this.settingsBinder.writeBean(settings);
			return Optional.of(settings);
		} catch (ValidationException e) {
			this.notificator.accept("Die Validierung mancher Felder ist fehlgeschlagen!");
			return Optional.empty();
		}
	}

	/**
	 * Diese Methode fügt eine neue Benachrichtigung hinzu.
	 *
	 * @param notification die hinzuzufügende Benachrichtigung
	 */
	public void addNotification(@Nonnull final String notification) {
		this.runWithUIAccess(
				() -> this.outputLayout.addComponentAsFirst(new Paragraph(notification)));
	}

	/**
	 * Diese Methode führt ein Kommando synchronisiert mit der UI aus.
	 *
	 * @param command das auszuführende Kommando
	 */
	private void runWithUIAccess(@Nonnull final Command command) {
		this.uiProvider.apply(this).ifPresent(ui -> ui.access(command));
	}

	/**
	 * Diese Methode gibt das {@link #brokerUrlField} zum Testen zurück.
	 *
	 * @return das Feld
	 */
	@Nonnull
	protected TextField getBrokerUrlField() {
		return this.brokerUrlField;
	}

	/**
	 * Diese Methode gibt das {@link #clientIdTextField} zum Testen zurück.
	 *
	 * @return das Feld
	 */
	@Nonnull
	public TextField getClientIdTextField() {
		return this.clientIdTextField;
	}

	/**
	 * Diese Methode gibt das {@link #usernameTextField} zum Testen zurück.
	 *
	 * @return das Feld
	 */
	@Nonnull
	public TextField getUsernameTextField() {
		return this.usernameTextField;
	}

	/**
	 * Diese Methode gibt das {@link #passwordField} zum Testen zurück.
	 *
	 * @return das Feld
	 */
	@Nonnull
	public PasswordField getPasswordField() {
		return this.passwordField;
	}

	/**
	 * Diese Methode gibt das {@link #topicsTextField} zum Testen zurück.
	 *
	 * @return das Feld
	 */
	@Nonnull
	public TextField getTopicsTextField() {
		return this.topicsTextField;
	}

	/**
	 * Diese Methode gibt das {@link #qosTextField} zum Testen zurück.
	 *
	 * @return das Feld
	 */
	@Nonnull
	public TextField getQosTextField() {
		return this.qosTextField;
	}

	/**
	 * Diese Methode gibt den {@link #connectButton} zum Testen zurück.
	 *
	 * @return der Button
	 */
	@Nonnull
	public Button getConnectButton() {
		return this.connectButton;
	}

	/**
	 * Diese Methode gibt den {@link #disconnectButton} zum Testen zurück.
	 *
	 * @return der Button
	 */
	@Nonnull
	public Button getDisconnectButton() {
		return this.disconnectButton;
	}
}
