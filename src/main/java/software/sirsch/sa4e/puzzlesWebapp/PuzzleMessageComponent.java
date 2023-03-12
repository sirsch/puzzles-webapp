package software.sirsch.sa4e.puzzlesWebapp;

import java.util.Optional;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.apache.commons.collections4.Factory;

/**
 * Diese Klasse stellt eine GUI-Komponente zur Anzeige einer Rätselnachricht bereit.
 *
 * @author sirsch
 * @since 11.03.2023
 */
public class PuzzleMessageComponent extends VerticalLayout {

	/**
	 * Dieses Feld muss die Überschrift enthalten.
	 */
	@Nonnull
	private final H4 headline = new H4();

	/**
	 * Dieses Feld muss die Zelle für die Server-ID enthalten.
	 */
	@Nonnull
	private final Div serverId = new Div();

	/**
	 * Dieses Feld muss die Zelle für die Rätsel-ID enthalten.
	 */
	@Nonnull
	private final Div puzzleId = new Div();

	/**
	 * Dieses Feld muss die Zelle für die Dauer enthalten.
	 */
	@Nonnull
	private final Div time = new Div();

	/**
	 * Dieses Feld muss das {@link Pre} für das Puzzle enthalten.
	 */
	@Nonnull
	private final Pre puzzle = new Pre();

	/**
	 * Dieses Feld muss die Zeile für die Dauer enthalten.
	 */
	@Nonnull
	private final Div timeRow = this.createTableRow("Dauer:", this.time);

	/**
	 * Dieses Feld muss die Fabrik für {@link PuzzlePrinter} enthalten.
	 */
	@Nonnull
	private final Factory<PuzzlePrinter> puzzlePrinterFactory;

	/**
	 * Dieser Konstruktor nimmt die interne Initialisierung vor.
	 */
	public PuzzleMessageComponent() {
		this(PuzzlePrinter::new);
	}

	/**
	 * Dieser Konstruktor erlaubt das Einschleusen von Objekten zum Testen.
	 *
	 * @param puzzlePrinterFactory die zu setzende Fabrik für {@link PuzzlePrinter}
	 */
	protected PuzzleMessageComponent(@Nonnull final Factory<PuzzlePrinter> puzzlePrinterFactory) {
		this.puzzlePrinterFactory = puzzlePrinterFactory;
		this.headline.getStyle()
				.set("margin", "0em");
		this.puzzle.getStyle()
				.set("margin", "0em")
				.set("padding", "1em");
		this.add(this.headline, this.createTable(), this.puzzle);
		this.getStyle()
				.set("background-color", "#121a24")
				.set("border-radius", "var(--lumo-border-radius-m)");
	}

	/**
	 * Diese Methode erzeugt die Tabelle für die Attributausgabe.
	 *
	 * @return die erzeugte Tabelle
	 */
	private Component createTable() {
		Div table = new Div();

		table.getStyle()
				.set("display", "table");
		table.add(
				this.createTableRow("Server-ID:", this.serverId),
				this.createTableRow("Rätsel-ID:", this.puzzleId),
				this.timeRow);
		return table;
	}

	/**
	 * Diese Methode erzeugt eine neue Tabellenzeile.
	 *
	 * @param text der zu verwendende Text
	 * @param valueCell die zu verwendende Zelle mit dem Wert
	 * @return die erzeugte Tabellenzeile
	 */
	@Nonnull
	private Div createTableRow(@CheckForNull final String text, @Nonnull final Div valueCell) {
		Div tableRow = new Div();

		tableRow.getStyle()
				.set("display", "table-row");
		valueCell.getStyle()
				.set("display", "table-cell");
		tableRow.add(this.createTableCell(text), valueCell);
		return tableRow;
	}

	/**
	 * Diese Methode erzeugt eine Tabellenzelle mit Text.
	 *
	 * @param text der zu verwendende Text
	 * @return die erzeugte Zelle
	 */
	@Nonnull
	private Div createTableCell(@CheckForNull final String text) {
		Div tableCell = new Div();

		tableCell.setText(text + "\u00A0");
		tableCell.getStyle()
				.set("display", "table-cell");
		return tableCell;
	}

	/**
	 * Diese Methode legt die Überschrift fest.
	 *
	 * @param headline die zu setzende Überschrift
	 */
	public void setHeadline(@CheckForNull final String headline) {
		this.headline.setText(headline);
	}

	/**
	 * Diese Methode legt das Rätsel fest.
	 *
	 * @param puzzle das zu setzende Rätsel
	 */
	public void setPuzzle(@CheckForNull final HasRows<?> puzzle) {
		this.puzzle.setText(
				Optional.ofNullable(puzzle)
						.map(this.puzzlePrinterFactory.create()::printToString)
						.orElse(null));
	}

	/**
	 * Diese Methode legt die Server-ID fest.
	 *
	 * @param serverId die zu setzende Server-ID
	 */
	public void setServerId(@CheckForNull final String serverId) {
		this.serverId.setText(serverId);
	}

	/**
	 * Diese Methode legt die Rätsel-ID fest.
	 *
	 * @param puzzleId die zu setzende Rätsel-ID
	 */
	public void setPuzzleId(@CheckForNull final Long puzzleId) {
		this.puzzleId.setText(
				Optional.ofNullable(puzzleId)
						.map(Object::toString)
						.orElse(null));
	}

	/**
	 * Diese Methode legt die Dauer fest.
	 *
	 * @param time die zu setzende Dauer
	 */
	public void setTime(@CheckForNull final Double time) {
		this.time.setText(
				Optional.ofNullable(time)
						.map(Object::toString)
						.orElse(null));
		this.timeRow.setVisible(time != null);
	}
}
