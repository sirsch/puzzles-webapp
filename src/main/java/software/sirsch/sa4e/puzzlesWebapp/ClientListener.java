package software.sirsch.sa4e.puzzlesWebapp;

import javax.annotation.Nonnull;

/**
 * Diese Schnittstelle beschreibt die Listener-Methoden für den Client.
 *
 * @author sirsch
 * @since 04.03.2023
 */
public interface ClientListener {

	/**
	 * Diese Methode behandelt eine neue Nachricht.
	 *
	 * @param message die zu behandelnde Nachricht
	 */
	void onMessage(@Nonnull String message);

	/**
	 * Diese Methode behandelt eine Benachrichtigung.
	 *
	 * @param notification die zu behandelnde Benachrichtigung
	 */
	void onNotification(@Nonnull String notification);
}
