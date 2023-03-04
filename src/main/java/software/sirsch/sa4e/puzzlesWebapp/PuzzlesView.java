package software.sirsch.sa4e.puzzlesWebapp;

import javax.annotation.Nonnull;

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
import com.vaadin.flow.router.Route;

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
	private TextField brokerUrlField = new TextField("MQTT Server URL");

	/**
	 * Dieses Feld muss das {@link TextField} für die Client-ID enthalten.
	 */
	@Nonnull
	private TextField clientIdTextField = new TextField("Client ID");

	/**
	 * Dieses Feld muss das {@link TextField} für den Benutzernamen enthalten.
	 */
	@Nonnull
	private TextField usernameTextField = new TextField("Benutzername");

	/**
	 * Dieses Feld muss das {@link PasswordField} enthalten.
	 */
	@Nonnull
	private PasswordField passwordField = new PasswordField("Passwort");

	/**
	 * Dieses Feld muss das {@link TextField} für die Topics enthalten.
	 */
	@Nonnull
	private TextField topicsTextField = new TextField("Topics");

	/**
	 * Dieses Feld muss das {@link TextField} für QOS enthalten.
	 */
	@Nonnull
	private TextField qosTextField = new TextField("QOS");

	/**
	 * Dieses Feld muss den {@link Button} zum Verbinden enthalten.
	 */
	@Nonnull
	private Button connectButton = new Button("verbinden");

	/**
	 * Dieser Konstruktor nimmt die interne Initialisierung vor.
	 */
	public PuzzlesView() {
		this.createLayout();
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
}
