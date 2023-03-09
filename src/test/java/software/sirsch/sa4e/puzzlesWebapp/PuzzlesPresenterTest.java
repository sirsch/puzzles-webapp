package software.sirsch.sa4e.puzzlesWebapp;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Diese Klasse stellt Tests für {@link PuzzlesPresenter} bereit.
 *
 * @author sirsch
 * @since 06.03.2023
 */
public class PuzzlesPresenterTest {

	/**
	 * Dieses Feld soll den Mock für die {@link Settings} enthalten.
	 */
	private Settings settings;

	/**
	 * Dieses Feld soll den Mock für die {@link Client} enthalten.
	 */
	private Client client;

	/**
	 * Dieses Feld soll den Mock für {@link ClientFactory} enthalten.
	 */
	private ClientFactory clientFactory;

	/**
	 * Dieses Feld soll den Mock für {@link ObjectMapper} enthalten.
	 */
	private ObjectMapper objectMapper;

	/**
	 * Dieses Feld soll den Mock für {@link PuzzlesView} enthalten.
	 */
	private PuzzlesView view;

	/**
	 * Dieses Feld soll das zu testende Objekt enthalten.
	 */
	private PuzzlesPresenter objectUnderTest;

	/**
	 * Diese Methode bereitet die Testumgebung für jeden Testfall vor.
	 */
	@BeforeEach
	public void setUp() {
		this.settings = mock(Settings.class);
		this.client = mock(Client.class);
		this.clientFactory = mock(ClientFactory.class);
		this.objectMapper = mock(ObjectMapper.class);
		this.view = mock(PuzzlesView.class);

		this.objectUnderTest = new PuzzlesPresenter(
				this.settings,
				this.clientFactory,
				this.objectMapper);

		when(this.clientFactory.create(this.settings, this.objectUnderTest))
				.thenReturn(this.client);
		this.objectUnderTest.init(this.view);
	}

	/**
	 * Diese Methode prüft {@link PuzzlesPresenter#init(PuzzlesView)}.
	 */
	@Test
	public void testInit() {
		/* init wird in #setUp aufgerufen */

		verify(this.view).readSettings(this.settings);
	}

	/**
	 * Diese Methode prüft, dass die fehlende View mittels {@link IllegalStateException} angezeigt
	 * wird.
	 */
	@Test
	public void testExceptionIfViewNotInitialized() {
		this.objectUnderTest = new PuzzlesPresenter();

		assertThrows(IllegalStateException.class, () -> this.objectUnderTest.onConnect());
	}

	/**
	 * Diese Methode prüft {@link PuzzlesPresenter#onConnect()}.
	 */
	@Test
	public void testOnConnect() {
		when(this.view.writeSettings(this.settings))
				.thenReturn(Optional.of(this.settings));

		this.objectUnderTest.onConnect();

		verify(this.clientFactory).create(this.settings, this.objectUnderTest);
		verify(this.view, never()).addNotification(anyString());
	}

	/**
	 * Diese Methode prüft die Ausnahmebehandlung bei {@link PuzzlesPresenter#onConnect()}.
	 */
	@Test
	public void testOnConnectException() {
		ClientException clientException = mock(ClientException.class);

		when(this.view.writeSettings(this.settings))
				.thenReturn(Optional.of(this.settings));
		when(clientException.getMessage())
				.thenReturn("testMessage");
		when(this.clientFactory.create(this.settings, this.objectUnderTest))
				.thenThrow(clientException);

		this.objectUnderTest.onConnect();

		verify(this.view).addNotification("testMessage");
	}

	/**
	 * Diese Methode prüft {@link PuzzlesPresenter#onDisconnect()}.
	 */
	@Test
	public void testOnDisconnect() {
		when(this.view.writeSettings(this.settings))
				.thenReturn(Optional.of(this.settings));
		this.objectUnderTest.onConnect();

		this.objectUnderTest.onDisconnect();

		verify(this.client).disconnect();
	}

	/**
	 * Diese Methode prüft {@link PuzzlesPresenter#onMessage(String)}.
	 *
	 * @throws JsonProcessingException wird in diesem Testfall nicht erwartet
	 */
	@Test
	public void testOnMessageRequest() throws JsonProcessingException {
		CommonSolvePuzzleRequest request = mock(CommonSolvePuzzleRequest.class);

		when(this.objectMapper.readValue("testRequest", CommonSolvePuzzleRequest.class))
				.thenReturn(request);
		when(request.validate()).thenReturn(true);

		this.objectUnderTest.onMessage("testRequest");

		verify(this.view).addRequest(request);
		verify(this.view, never()).addResponse(any());
		verify(this.view, never()).addNotification(any());
	}

	/**
	 * Diese Methode prüft {@link PuzzlesPresenter#onMessage(String)}, wenn die Nachricht nicht
	 * valide ist.
	 *
	 * @throws JsonProcessingException wird in diesem Testfall nicht erwartet
	 */
	@Test
	public void testOnMessageRequestInvalid() throws JsonProcessingException {
		CommonSolvePuzzleRequest request = mock(CommonSolvePuzzleRequest.class);

		when(this.objectMapper.readValue("testRequest", CommonSolvePuzzleRequest.class))
				.thenReturn(request);
		when(request.validate()).thenReturn(false);

		this.objectUnderTest.onMessage("testRequest");

		verify(this.view, never()).addRequest(any());
		verify(this.view, never()).addResponse(any());
		verify(this.view).addNotification(notNull());
	}

	/**
	 * Diese Methode prüft {@link PuzzlesPresenter#onMessage(String)}, wenn eine
	 * {@link JsonProcessingException} fliegt.
	 *
	 * @throws JsonProcessingException wird in diesem Testfall nicht erwartet
	 */
	@Test
	public void testOnMessageRequestJsonProcessingException() throws JsonProcessingException {
		JsonProcessingException exception = mock(JsonProcessingException.class);

		when(this.objectMapper.readValue("testRequest", CommonSolvePuzzleRequest.class))
				.thenThrow(exception);

		this.objectUnderTest.onMessage("testRequest");

		verify(this.view, never()).addRequest(any());
		verify(this.view, never()).addResponse(any());
		verify(this.view).addNotification(notNull());
	}

	/**
	 * Diese Methode prüft {@link PuzzlesPresenter#onMessage(String)}.
	 *
	 * @throws JsonProcessingException wird in diesem Testfall nicht erwartet
	 */
	@Test
	public void testOnMessageResponse() throws JsonProcessingException {
		CommonSolvePuzzleResponse response = mock(CommonSolvePuzzleResponse.class);

		when(this.objectMapper.readValue("testRequest", CommonSolvePuzzleResponse.class))
				.thenReturn(response);
		when(response.validate()).thenReturn(true);

		this.objectUnderTest.onMessage("testRequest");

		verify(this.view, never()).addRequest(any());
		verify(this.view).addResponse(response);
		verify(this.view, never()).addNotification(any());
	}

	/**
	 * Diese Methode prüft {@link PuzzlesPresenter#onNotification(String)}.
	 */
	@Test
	public void testOnNotification() {
		this.objectUnderTest.onNotification("testNotification");

		verify(this.view).addNotification("testNotification");
	}
}