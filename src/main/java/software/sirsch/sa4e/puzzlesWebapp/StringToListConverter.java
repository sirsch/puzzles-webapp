package software.sirsch.sa4e.puzzlesWebapp;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import org.apache.commons.lang3.StringUtils;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.split;

/**
 * Diese Klasse stellt einen {@link Converter} von String nach List-String bereit.
 *
 * @author sirsch
 * @since 05.03.2023
 */
public class StringToListConverter implements Converter<String, List<String>> {

	@Override
	@Nonnull
	public Result<List<String>> convertToModel(
			@CheckForNull final String value,
			@Nonnull final ValueContext context) {

		return Result.ok(this.convertToModel(value));
	}

	/**
	 * Diese Methode spaltet die Eingabe am Zeichen {@code ','} und trimmt die Bestandteile.
	 *
	 * @param value die zu verarbeitende Zeichenkette
	 * @return die resultierende Liste von Zeichenketten
	 */
	@Nonnull
	private List<String> convertToModel(@CheckForNull final String value) {
		if (value == null) {
			return emptyList();
		}

		return Stream.of(split(value, ','))
				.map(StringUtils::trimToNull)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	@Override
	@CheckForNull
	public String convertToPresentation(
			@CheckForNull final List<String> value,
			@Nonnull final ValueContext context) {

		if (value == null) {
			return "";
		}

		return value.stream()
				.collect(Collectors.joining(", "));
	}
}
