package software.sirsch.sa4e.puzzlesWebapp;

import java.util.List;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Diese Klasse stellt Tests für {@link PuzzlePrinter} bereit.
 *
 * @author sirsch
 * @since 18.12.2022
 */
public class PuzzlePrinterTest {

	/**
	 * Dieses Feld soll das zu testende Objekt enthalten.
	 */
	private PuzzlePrinter objectUnderTest;

	/**
	 * Diese Methode bereitet die Testumgebung für jeden Testfall vor.
	 */
	@BeforeEach
	public void setUp() {
		this.objectUnderTest = new PuzzlePrinter();
	}

	/**
	 * Diese Methode prüft {@link PuzzlePrinter#printToString(HasRows)} mit einem
	 * {@link CommonSolvePuzzleRequest}.
	 */
	@Test
	public void testPrintRequest() {
		String expected
				= " A + A =   A\n"
				+ "AB + B =   B\n"
				+ " B + B = BAB\n";
		String result;

		result = this.objectUnderTest.printToString(this.generateRequest());

		assertEquals(expected, result);
	}

	/**
	 * Diese Methode erzeugt ein einfaches Puzzle als Anfrage.
	 *
	 * @return das erzeugte Puzzle
	 */
	@Nonnull
	private CommonSolvePuzzleRequest generateRequest() {
		CommonSolvePuzzleRequest request = new CommonSolvePuzzleRequest();

		request.setRow1(List.of("A", "A", "A"));
		request.setRow2(List.of("AB", "B", "B"));
		request.setRow3(List.of("B", "B", "BAB"));
		return request;
	}

	/**
	 * Diese Methode prüft {@link PuzzlePrinter#printToString(HasRows)} mit einer
	 * {@link CommonSolvePuzzleResponse}.
	 */
	@Test
	public void testPrintResponse() {
		String expected
				= " 1 + 1 =   1\n"
				+ "12 + 2 =   2\n"
				+ " 2 + 2 = 212\n";
		String result;

		result = this.objectUnderTest.printToString(this.generateResponse());

		assertEquals(expected, result);
	}

	/**
	 * Diese Methode erzeugt ein einfaches Puzzle als Antwort.
	 *
	 * @return das erzeugte Puzzle
	 */
	@Nonnull
	private CommonSolvePuzzleResponse generateResponse() {
		CommonSolvePuzzleResponse response = new CommonSolvePuzzleResponse();

		response.setRow1(List.of(1, 1, 1));
		response.setRow2(List.of(12, 2, 2));
		response.setRow3(List.of(2, 2, 212));
		return response;
	}
}
