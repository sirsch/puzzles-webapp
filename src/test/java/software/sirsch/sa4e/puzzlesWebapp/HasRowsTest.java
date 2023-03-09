package software.sirsch.sa4e.puzzlesWebapp;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Diese Klasse stellt Tests für {@link HasRows} bereit.
 *
 * @author sirsch
 * @since 09.03.2023
 */
public class HasRowsTest {

	/**
	 * Dieses Feld soll das zu testende Objekt enthalten.
	 */
	private HasRows<Integer> objectUnderTest;

	/**
	 * Diese Methode bereitet die Testumgebung für jeden Testfall vor.
	 */
	@BeforeEach
	public void setUp() {
		this.objectUnderTest = spy(HasRows.class);
	}

	/**
	 * Diese Methode prüft {@link HasRows#validate()}.
	 */
	@Test
	public void testValidate() {
		boolean result;

		when(this.objectUnderTest.getRow1()).thenReturn(List.of(0, 1, 2));
		when(this.objectUnderTest.getRow2()).thenReturn(List.of(0, 1, 2));
		when(this.objectUnderTest.getRow3()).thenReturn(List.of(0, 1, 2));

		result = this.objectUnderTest.validate();

		assertTrue(result);
	}

	/**
	 * Diese Methode prüft {@link HasRows#validate()}, wenn Row 1 ungültig ist.
	 */
	@Test
	public void testValidateInvalidRow1() {
		boolean result;

		when(this.objectUnderTest.getRow1()).thenReturn(null);
		when(this.objectUnderTest.getRow2()).thenReturn(List.of(0, 1, 2));
		when(this.objectUnderTest.getRow3()).thenReturn(List.of(0, 1, 2));

		result = this.objectUnderTest.validate();

		assertFalse(result);
	}

	/**
	 * Diese Methode prüft {@link HasRows#validate()}, wenn Row 2 ungültig ist.
	 */
	@Test
	public void testValidateInvalidRow2() {
		boolean result;

		when(this.objectUnderTest.getRow1()).thenReturn(List.of(0, 1, 2));
		when(this.objectUnderTest.getRow2()).thenReturn(null);
		when(this.objectUnderTest.getRow3()).thenReturn(List.of(0, 1, 2));

		result = this.objectUnderTest.validate();

		assertFalse(result);
	}

	/**
	 * Diese Methode prüft {@link HasRows#validate()}, wenn Row 3 ungültig ist.
	 */
	@Test
	public void testValidateInvalidRow3() {
		boolean result;

		when(this.objectUnderTest.getRow1()).thenReturn(List.of(0, 1, 2));
		when(this.objectUnderTest.getRow2()).thenReturn(List.of(0, 1, 2));
		when(this.objectUnderTest.getRow3()).thenReturn(null);

		result = this.objectUnderTest.validate();

		assertFalse(result);
	}

	/**
	 * Diese Methode prüft {@link HasRows#validateRow(List)}.
	 */
	@Test
	public void testValidateRow() {
		boolean result;

		result = this.objectUnderTest.validateRow(List.of(0, 1, 2));

		assertTrue(result);
	}

	/**
	 * Diese Methode prüft {@link HasRows#validateRow(List)} mit {@code null}.
	 */
	@Test
	public void testValidateRowNull() {
		boolean result;

		result = this.objectUnderTest.validateRow(null);

		assertFalse(result);
	}

	/**
	 * Diese Methode prüft {@link HasRows#validateRow(List)} mit einer Liste einer ungültigen Länge.
	 */
	@Test
	public void testValidateRowWrongSize() {
		boolean result;

		result = this.objectUnderTest.validateRow(List.of(0, 1));

		assertFalse(result);
	}
}
