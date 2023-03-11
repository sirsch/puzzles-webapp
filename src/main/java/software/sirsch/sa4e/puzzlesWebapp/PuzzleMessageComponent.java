package software.sirsch.sa4e.puzzlesWebapp;

import javax.annotation.CheckForNull;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Diese Klasse stellt eine GUI-Komponente zur Anzeige einer Rätselnachricht bereit.
 *
 * @author sirsch
 * @since 11.03.2023
 */
public class PuzzleMessageComponent extends VerticalLayout {

	/**
	 * Diese Methode legt das Rätsel fest.
	 *
	 * @param puzzle das zu setzende Rätsel
	 */
	public void setPuzzle(@CheckForNull final HasRows<?> puzzle) {
	}

	/**
	 * Diese Methode legt die Server-ID fest.
	 *
	 * @param serverId die zu setzende Server-ID
	 */
	public void setServerId(@CheckForNull final String serverId) {
	}

	/**
	 * Diese Methode legt die Rätsel-ID fest.
	 *
	 * @param puzzleId die zu setzende Rätsel-ID
	 */
	public void setPuzzleId(@CheckForNull final Long puzzleId) {
	}

	/**
	 * Diese Methode legt die Dauer fest.
	 *
	 * @param time die zu setzende Dauer
	 */
	public void setTime(@CheckForNull final Double time) {
	}
}
