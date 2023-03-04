package software.sirsch.sa4e.puzzlesWebapp;

import javax.annotation.Nonnull;

/**
 * Diese Schnittstelle beschreibt die Fabrikmethode für {@link ClientCallbackAdapter}.
 *
 * @author sirsch
 * @since 04.03.2023
 */
@FunctionalInterface
public interface ClientCallbackAdapterFactory {

	/**
	 * Diese Methode erzeugt einen neuen {@link ClientCallbackAdapter}.
	 *
	 * @param clientListener der zu verwendende {@link ClientListener}
	 * @return die erzeugte Instanz
	 */
	@Nonnull
	ClientCallbackAdapter create(@Nonnull ClientListener clientListener);
}
