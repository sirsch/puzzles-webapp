package software.sirsch.sa4e.puzzlesWebapp;

import org.apache.commons.collections4.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Diese Klasse stellt Tests für {@link PuzzleMessageComponent} bereit.
 *
 * @author sirsch
 * @since 13.03.2023
 */
public class PuzzleMessageComponentTest {

	/**
	 * Dieses Feld soll den Mock für die Fabrik für {@link PuzzlePrinter} enthalten.
	 */
	private Factory<PuzzlePrinter> puzzlePrinterFactory;

	/**
	 * Dieses Feld soll das zu testende Objekt enthalten.
	 */
	private PuzzleMessageComponent objectUnderTest;

	/**
	 * Diese Methode bereitet die Testumgebung für jeden Testfall vor.
	 */
	@BeforeEach
	public void setUp() {
		this.puzzlePrinterFactory = mock(Factory.class);

		this.objectUnderTest = new PuzzleMessageComponent(this.puzzlePrinterFactory);
	}

	/**
	 * Diese Methode prüft {@link PuzzleMessageComponent#setHeadline(String)}.
	 */
	@Test
	public void testSetHeadline() {
		this.objectUnderTest = new PuzzleMessageComponent();

		this.objectUnderTest.setHeadline("testHeadline");

		assertEquals("testHeadline", this.objectUnderTest.getHeadline().getText());
	}

	/**
	 * Diese Methode prüft {@link PuzzleMessageComponent#setPuzzle(HasRows)}.
	 */
	@Test
	public void testSetPuzzle() {
		PuzzlePrinter puzzlePrinter = mock(PuzzlePrinter.class);
		HasRows<?> puzzle = mock(HasRows.class);

		when(puzzlePrinter.printToString(puzzle)).thenReturn("testPuzzle");
		when(this.puzzlePrinterFactory.create()).thenReturn(puzzlePrinter);

		this.objectUnderTest.setPuzzle(puzzle);

		assertEquals("testPuzzle", this.objectUnderTest.getPuzzle().getText());
	}

	/**
	 * Diese Methode prüft {@link PuzzleMessageComponent#setServerId(String)}.
	 */
	@Test
	public void testSetServerId() {
		this.objectUnderTest.setServerId("testServerId");

		assertEquals("testServerId", this.objectUnderTest.getServerId().getText());
	}

	/**
	 * Diese Methode prüft {@link PuzzleMessageComponent#setPuzzleId(Long)}.
	 */
	@Test
	public void testSetPuzzleId() {
		this.objectUnderTest.setPuzzleId(42L);

		assertEquals("42", this.objectUnderTest.getPuzzleId().getText());
	}

	/**
	 * Diese Methode prüft {@link PuzzleMessageComponent#setTime(Double)}.
	 */
	@Test
	public void testSetTime() {
		this.objectUnderTest.setTime(0.5);

		assertEquals("0.5", this.objectUnderTest.getTime().getText());
		assertTrue(this.objectUnderTest.getTimeRow().isVisible());
	}

	/**
	 * Diese Methode prüft {@link PuzzleMessageComponent#setTime(Double)}.
	 */
	@Test
	public void testSetTimeNull() {
		this.objectUnderTest.setTime(null);

		assertFalse(this.objectUnderTest.getTimeRow().isVisible());
	}
}