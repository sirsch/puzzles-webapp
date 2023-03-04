package software.sirsch.sa4e.puzzlesWebapp;

import javax.annotation.Nonnull;

/**
 * Diese Klasse stellt eine Ausnahme, die Client-Fehler anzeigt, bereit.
 *
 * @author sirsch
 * @since 04.03.2023
 */
public class ClientException extends RuntimeException {

	/**
	 * Dieser Konstruktor legt Nachricht und Ursache fest.
	 *
	 * @param cause die zu setzende Ursache
	 */
	public ClientException(@Nonnull final Throwable cause) {
		super(cause);
	}
}
