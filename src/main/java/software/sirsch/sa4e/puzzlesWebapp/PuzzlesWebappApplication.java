package software.sirsch.sa4e.puzzlesWebapp;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Diese Klasse stellt die Spring-Boot-Anwendung bereit.
 *
 * @author sirsch
 */
@SpringBootApplication
public class PuzzlesWebappApplication {

	/**
	 * Dieses Feld zeigt an, dass es sich nicht um eine Hilfsklasse handelt.
	 */
	@CheckForNull
	private String notAUtilityClass;

	/**
	 * Diese Methode startet und betreibt die Anwendung.
	 *
	 * @param args die zu verwendenden Argumente
	 */
	public static void main(@Nonnull final String[] args) {
		SpringApplication.run(PuzzlesWebappApplication.class, args);
	}
}
