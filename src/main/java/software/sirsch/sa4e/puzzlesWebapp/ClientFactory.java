package software.sirsch.sa4e.puzzlesWebapp;

import javax.annotation.Nonnull;

/**
 * Diese Schnittstelle beschreibt die Fabrikmethode für {@link Client}.
 *
 * @author sirsch
 * @since 06.03.2023
 */
@FunctionalInterface
public interface ClientFactory {

	/**
	 * Diese Methode erzeugt einen neuen {@link Client}.
	 *
	 * @param settings die zu verwendenden Einstellungen
	 * @param clientListener der zu verwendende {@link ClientListener}
	 * @return die erzeugte Instanz
	 */
	@Nonnull
	Client create(@Nonnull Settings settings, @Nonnull ClientListener clientListener);
}
