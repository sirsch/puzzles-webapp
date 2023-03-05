package software.sirsch.sa4e.puzzlesWebapp;

import java.util.Optional;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.vaadin.flow.spring.annotation.UIScope;

import org.springframework.stereotype.Component;

/**
 * Diese Klasse stellt einen Presenter für {@link PuzzlesView} bereit.
 *
 * @author sirsch
 * @since 05.03.2023
 */
@Component
@UIScope
public class PuzzlesPresenter {

	/**
	 * Dieses Feld muss die zu verwendenden Einstellungen enthalten.
	 */
	@Nonnull
	private Settings settings = new Settings();

	/**
	 * Dieses Feld soll die {@link PuzzlesView} enthalten, für die dieser Presenter zuständig ist.
	 */
	@CheckForNull
	private PuzzlesView view;

	/**
	 * Diese Methode initialisiert diese Instanz mit der zu verwaltenden View.
	 *
	 * @param puzzlesView die zu setzende View
	 */
	public void init(@Nonnull final PuzzlesView puzzlesView) {
		this.view = puzzlesView;
		this.view.readSettings(this.settings);
	}

	/**
	 * Diese Methode gibt die View zurück, unter der Annahme, dass die View vorhanden ist.
	 *
	 * @return die View
	 * @throws IllegalStateException falls die View nicht gesetzt ist
	 */
	@Nonnull
	private PuzzlesView requireView() {
		return Optional.ofNullable(this.view)
				.orElseThrow(IllegalStateException::new);
	}
}
