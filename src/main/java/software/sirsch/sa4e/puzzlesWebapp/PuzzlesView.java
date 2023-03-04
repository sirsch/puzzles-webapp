package software.sirsch.sa4e.puzzlesWebapp;

import javax.annotation.Nonnull;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
	private TextField brokerURLField = new TextField("MQTT-Broker-URL");

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
		this.add(this.createForm());
	}

	/**
	 * Diese Methode erzeugt das Layout für das Formular.
	 *
	 * @return das erzeugte Formular
	 */
	private Component createForm() {
		FormLayout formLayout = new FormLayout(this.brokerURLField);

		return formLayout;
	}
}
