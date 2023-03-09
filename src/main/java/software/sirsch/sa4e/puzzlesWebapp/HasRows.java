package software.sirsch.sa4e.puzzlesWebapp;

import java.util.List;

import javax.annotation.CheckForNull;

public interface HasRows<T> {

	/**
	 * Diese Methode gibt die erste Zeile zurück.
	 *
	 * @return die Zeile, falls vorhanden
	 */
	@CheckForNull
	List<T> getRow1();

	/**
	 * Diese Methode legt die erste Zeile fest.
	 *
	 * @param row1 die zu setzende Zeile
	 */
	void setRow1(@CheckForNull List<T> row1);

	/**
	 * Diese Methode gibt die zweite Zeile zurück.
	 *
	 * @return die Zeile, falls vorhanden
	 */
	@CheckForNull
	List<T> getRow2();

	/**
	 * Diese Methode legt die zweite Zeile fest.
	 *
	 * @param row2 die zu setzende Zeile
	 */
	void setRow2(@CheckForNull List<T> row2);

	/**
	 * Diese Methode gibt die dritte Zeile zurück.
	 *
	 * @return die Zeile, falls vorhanden
	 */
	@CheckForNull
	List<T> getRow3();

	/**
	 * Diese Methode legt die dritte Zeile fest.
	 *
	 * @param row3 die zu setzende Zeile
	 */
	void setRow3(@CheckForNull List<T> row3);

	/**
	 * Diese Methode prüft, ob die Zeilen jeweils drei Elemente enthalten.
	 *
	 * @return {@code true}, falls jede Zeile drei Elemente enthält, sonst {@code false}
	 */
	default boolean validate() {
		return this.validateRow(this.getRow1())
				&& this.validateRow(this.getRow2())
				&& this.validateRow(this.getRow3());
	}

	/**
	 * Diese Methode prüft, ob eine Zeile Elemente enthält.
	 *
	 * @param row die zu prüfende Zeile
	 * @return {@code true}, falls die Zeile drei Elemente enthält, sonst {@code false}
	 */
	default boolean validateRow(@CheckForNull List<T> row) {
		final int expectedSize = 3;

		return row != null && row.size() == expectedSize;
	}
}
