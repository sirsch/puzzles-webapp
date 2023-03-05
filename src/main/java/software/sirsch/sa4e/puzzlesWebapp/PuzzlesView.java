package software.sirsch.sa4e.puzzlesWebapp;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;

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
	private final TextField brokerUrlField = new TextField("MQTT Server URL");

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
	 * Dieses Feld muss den {@link Binder} für die Formularfelder enthalten.
	 */
	@Nonnull
	private final Binder<Settings> settingsBinder = new Binder<>(Settings.class);

	/**
	 * Dieses Feld muss den {@link PuzzlesPresenter} enthalten.
	 */
	@Nonnull
	private final PuzzlesPresenter presenter;

	/**
	 * Dieser Konstruktor nimmt die interne Initialisierung vor.
	 *
	 * @param presenter der zu setzende Presenter
	 */
	@Autowired
	public PuzzlesView(@Nonnull final PuzzlesPresenter presenter) {
		this.presenter = presenter;
		this.initializeBinder();
		this.createLayout();
	}

	/**
	 * Diese Methode initialisiert den {@link #settingsBinder}.
	 */
	private void initializeBinder() {
		this.settingsBinder.forField(this.brokerUrlField)
				.bind(Settings::getBrokerUrl, Settings::setBrokerUrl);
		this.settingsBinder.forField(this.clientIdTextField)
				.bind(Settings::getClientId, Settings::setClientId);
		this.settingsBinder.forField(this.usernameTextField)
				.bind(Settings::getUsername, Settings::setUsername);
		this.settingsBinder.forField(this.passwordField)
				.bind(Settings::getPassword, Settings::setPassword);
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

		this.connectButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		buttonLayout.add(this.connectButton);
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
}
