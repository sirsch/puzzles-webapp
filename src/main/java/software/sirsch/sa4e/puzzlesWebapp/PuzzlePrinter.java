package software.sirsch.sa4e.puzzlesWebapp;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

/**
 * Diese Klasse stellt die Funktionalität zur textbasierten Ausgabe eines Puzzles bereit.
 *
 * @author sirsch
 * @since 18.12.2022
 */
public class PuzzlePrinter {

	/**
	 * Dieses Feld enthält den {@link StringBuilder} zum Generieren der Zeilen.
	 */
	@Nonnull
	private final StringBuilder stringBuilder = new StringBuilder();

	/**
	 * Dieses Feld enthält die Zellen als Liste von Spalten.
	 */
	@Nonnull
	private HasRows<?> currentPuzzle = new CommonSolvePuzzleRequest();

	/**
	 * Diese Methode gibt ein Rätsel in einer lesbaren Darstellung aus.
	 *
	 * @param puzzle das auszugebende Rätsel
	 * @return die Ausgabe als Zeichenkette
	 */
	public String printToString(@Nonnull final HasRows<?> puzzle) {
		this.stringBuilder.setLength(0);
		this.currentPuzzle = puzzle;
		this.addRow(0);
		this.addRow(1);
		this.addRow(2);
		return this.stringBuilder.toString();
	}

	/**
	 * Diese Methode erzeugt die Zeile.
	 *
	 * @param row die Nummer der Zeile
	 */
	private void addRow(final int row) {
		this.appendCell(row, 0);
		this.stringBuilder.append(" + ");
		this.appendCell(row, 1);
		this.stringBuilder.append(" = ");
		this.appendCell(row, 2);
		this.stringBuilder.append('\n');
	}

	/**
	 * Diese Methode fügt die Darstellung eine Zelle in den {@link #stringBuilder} ein.
	 *
	 * @param row die Zeilennummer
	 * @param column die Spaltennummer
	 */
	private void appendCell(final int row, final int column) {
		this.appendCell(column, this.streamRows().toList().get(row).get(column));
	}

	/**
	 * Diese Methode fügt die Darstellung eine Zelle in den {@link #stringBuilder} ein.
	 *
	 * @param column die Spaltennummer
	 * @param cell der Inhalt der Zelle
	 */
	private void appendCell(final int column, @Nonnull final Object cell) {
		this.appendCell(column, cell.toString());
	}

	/**
	 * Diese Methode fügt die Darstellung eine Zelle in den {@link #stringBuilder} ein.
	 *
	 * @param column die Spaltennummer
	 * @param cell der Inhalt der Zelle
	 */
	private void appendCell(final int column, @Nonnull final String cell) {
		this.addCellPadding(column, cell.length());
		this.stringBuilder.append(cell);
	}

	/**
	 * Diese Methode fügt den Abstand für eine Zelle ein.
	 *
	 * @param column die Spaltennummer
	 * @param cellSize die Größe des Inhalts der Zelle
	 */
	private void addCellPadding(final int column, final int cellSize) {
		this.addPadding(this.findColumnWidth(column) - cellSize);
	}

	/**
	 * Diese Methode ermittelt die Breite einer Spalte.
	 *
	 * @param column die Spaltennummer
	 * @return die ermittelte Breite
	 */
	private int findColumnWidth(final int column) {
		return this.streamRows()
				.map(row -> row.get(column))
				.map(Object::toString)
				.mapToInt(String::length)
				.max()
				.orElse(0);
	}

	/**
	 * Diese Methode fügt den Abstand in den {@link #stringBuilder} ein.
	 *
	 * @param padding die Anzahl der Zeichen für den Abstand
	 */
	private void addPadding(final int padding) {
		IntStream.range(0, padding).forEach(ignore -> this.stringBuilder.append(' '));
	}

	/**
	 * Diese Methode gibt einen Stream der Zeilen zurück.
	 *
	 * @return der {@link Stream}
	 */
	@Nonnull
	private Stream<List<?>> streamRows() {
		return Stream.of(
				this.currentPuzzle.getRow1(),
				this.currentPuzzle.getRow2(),
				this.currentPuzzle.getRow3())
				.filter(Objects::nonNull);
	}
}
