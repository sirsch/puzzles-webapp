package software.sirsch.sa4e.puzzlesWebapp;

import java.util.List;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Diese Klasse stellt Tests für {@link StringToListConverter} bereit.
 *
 * @author sirsch
 * @since 05.03.2023
 */
public class StringToListConverterTest {

	/**
	 * Dieses Feld soll den Mock für {@link ValueContext} enthalten.
	 */
	private ValueContext valueContext;

	/**
	 * Dieses Feld soll das zu testende Objekt enthalten.
	 */
	private StringToListConverter objectUnderTest;

	/**
	 * Diese Methode bereitet die Testumgebung für jeden Testfall vor.
	 */
	@BeforeEach
	public void setUp() {
		this.valueContext = mock(ValueContext.class);

		this.objectUnderTest = new StringToListConverter();
	}

	/**
	 * Diese Methode prüft {@link StringToListConverter#convertToModel(String, ValueContext)} mit
	 * {@code null}.
	 */
	@Test
	public void testConvertToModelNull() {
		Result<List<String>> result;

		result = this.objectUnderTest.convertToModel(null, this.valueContext);

		assertNotNull(result);
		assertFalse(result.isError());
		assertEquals(emptyList(), result.getOrThrow(AssertionError::new));
	}

	/**
	 * Diese Methode prüft {@link StringToListConverter#convertToModel(String, ValueContext)} mit
	 * einer Zeichenkette die nur aus Leerzeichen besteht.
	 */
	@Test
	public void testConvertToModelBlank() {
		Result<List<String>> result;

		result = this.objectUnderTest.convertToModel("\t \n", this.valueContext);

		assertNotNull(result);
		assertFalse(result.isError());
		assertEquals(emptyList(), result.getOrThrow(AssertionError::new));
	}

	/**
	 * Diese Methode prüft {@link StringToListConverter#convertToModel(String, ValueContext)}.
	 */
	@Test
	public void testConvertToModel() {
		Result<List<String>> result;

		result = this.objectUnderTest.convertToModel("test0, test1 , ", this.valueContext);

		assertNotNull(result);
		assertFalse(result.isError());
		assertEquals(List.of("test0", "test1"), result.getOrThrow(AssertionError::new));
	}

	/**
	 * Diese Methode prüft {@link StringToListConverter#convertToPresentation(List, ValueContext)}.
	 */
	@Test
	public void testConvertToPresentationNull() {
		String result;

		result = this.objectUnderTest.convertToPresentation(null, this.valueContext);

		assertEquals("", result);
	}

	/**
	 * Diese Methode prüft {@link StringToListConverter#convertToPresentation(List, ValueContext)}.
	 */
	@Test
	public void testConvertToPresentation() {
		String result;

		result = this.objectUnderTest.convertToPresentation(
				List.of("test0", "test1"),
				this.valueContext);

		assertEquals("test0, test1", result);
	}
}
