package software.sirsch.sa4e.puzzlesWebapp;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
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
	 * Dieses Feld soll den Mock für die {@link ClientFactory} enthalten.
	 */
	private ClientFactory clientFactory;

	/**
	 * Dieses Feld soll den Mock für die {@link PuzzlesView} enthalten.
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
		this.view = mock(PuzzlesView.class);

		this.objectUnderTest = new PuzzlesPresenter(this.settings, this.clientFactory);

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
	 */
	@Test
	public void testOnMessage() {
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