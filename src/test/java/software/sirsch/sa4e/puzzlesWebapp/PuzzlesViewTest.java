package software.sirsch.sa4e.puzzlesWebapp;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.server.Command;

import org.apache.commons.collections4.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Diese Klasse stellt Tests für {@link PuzzlesView} bereit.
 *
 * @author sirsch
 * @since 06.03.2023
 */
public class PuzzlesViewTest {

	/**
	 * Dieses Feld soll den Mock für {@link PuzzlesPresenter} enthalten.
	 */
	private PuzzlesPresenter presenter;

	/**
	 * Dieses Feld soll den Mock für den Notificator enthalten.
	 */
	private Consumer<String> notificator;

	/**
	 * Dieses Feld soll den Mock für den UI-Provider enthalten.
	 */
	private Function<Component, Optional<UI>> uiProvider;

	/**
	 * Dieses Feld soll den Mock für {@link PuzzleMessageComponent} enthalten.
	 */
	private PuzzleMessageComponent puzzleMessageComponent;

	/**
	 * Dieses Feld soll den Mock für die Fabrik für {@link PuzzleMessageComponent} enthalten.
	 */
	private Factory<PuzzleMessageComponent> puzzleMessageComponentFactory;

	/**
	 * Dieses Feld soll das zu testende Objekt enthalten.
	 */
	private PuzzlesView objectUnderTest;

	/**
	 * Diese Methode bereitet die Testumgebung für jeden Testfall vor.
	 */
	@BeforeEach
	public void setUp() {
		UI ui = mock(UI.class);

		this.presenter = mock(PuzzlesPresenter.class);
		this.notificator = mock(Consumer.class);
		this.uiProvider = mock(Function.class);
		this.puzzleMessageComponent = spy(PuzzleMessageComponent.class);
		this.puzzleMessageComponentFactory = mock(Factory.class);
		doAnswer(invocation -> {
			invocation.<Command>getArgument(0).execute();
			return null;
		}).when(ui).access(notNull());
		when(this.uiProvider.apply(any())).thenReturn(Optional.empty());
		doNothing().when(this.puzzleMessageComponent).setPuzzle(any());
		when(this.puzzleMessageComponentFactory.create()).thenReturn(
				this.puzzleMessageComponent,
				mock(PuzzleMessageComponent.class));

		this.objectUnderTest = new PuzzlesView(
				this.presenter,
				this.notificator,
				this.uiProvider,
				this.puzzleMessageComponentFactory);

		when(this.uiProvider.apply(this.objectUnderTest)).thenReturn(Optional.of(ui));
	}

	/**
	 * Diese Methode prüft {@link PuzzlesView#init()}.
	 */
	@Test
	public void testInit() {
		this.objectUnderTest = new PuzzlesView(this.presenter);

		this.objectUnderTest.init();

		verify(this.presenter).init(this.objectUnderTest);
	}

	/**
	 * Diese Methode prüft {@link PuzzlesView#readSettings(Settings)}.
	 */
	@Test
	public void testReadSettings() {
		Settings settings = mock(Settings.class);

		when(settings.getBrokerUrl()).thenReturn("test://broker.url");
		when(settings.getClientId()).thenReturn("testClientId");
		when(settings.getUsername()).thenReturn("testUsername");
		when(settings.getPassword()).thenReturn("testPassword");
		when(settings.getTopics()).thenReturn(List.of("testTopic0", "testTopic1"));
		when(settings.getQos()).thenReturn(2);

		this.objectUnderTest.readSettings(settings);

		assertEquals(
				"test://broker.url",
				this.objectUnderTest.getBrokerUrlField().getValue());
		assertEquals(
				"testClientId",
				this.objectUnderTest.getClientIdTextField().getValue());
		assertEquals(
				"testUsername",
				this.objectUnderTest.getUsernameTextField().getValue());
		assertEquals(
				"testPassword",
				this.objectUnderTest.getPasswordField().getValue());
		assertEquals(
				"testTopic0, testTopic1",
				this.objectUnderTest.getTopicsTextField().getValue());
		assertEquals(
				"2",
				this.objectUnderTest.getQosTextField().getValue());
	}

	/**
	 * Diese Methode prüft {@link PuzzlesView#writeSettings(Settings)}, wenn die Validierung
	 * fehlschlägt.
	 */
	@Test
	public void testWriteSettingsValidationFailed() {
		Settings settings = mock(Settings.class);
		Optional<Settings> result;

		this.objectUnderTest.getBrokerUrlField().setValue("");

		result = this.objectUnderTest.writeSettings(settings);

		assertEquals(Optional.empty(), result);
		verifyNoInteractions(settings);
		verify(this.notificator).accept("Die Validierung mancher Felder ist fehlgeschlagen!");
	}

	/**
	 * Diese Methode prüft {@link PuzzlesView#writeSettings(Settings)}.
	 */
	@Test
	public void testWriteSettings() {
		Settings settings = mock(Settings.class);
		Optional<Settings> result;

		this.objectUnderTest.getBrokerUrlField().setValue("test://broker.url");
		this.objectUnderTest.getClientIdTextField().setValue("testClientId");
		this.objectUnderTest.getUsernameTextField().setValue("testUsername");
		this.objectUnderTest.getPasswordField().setValue("testPassword");
		this.objectUnderTest.getTopicsTextField().setValue("testTopic0, testTopic1");
		this.objectUnderTest.getQosTextField().setValue("2");

		result = this.objectUnderTest.writeSettings(settings);

		assertEquals(Optional.of(settings), result);
		verify(settings).setBrokerUrl("test://broker.url");
		verify(settings).setClientId("testClientId");
		verify(settings).setUsername("testUsername");
		verify(settings).setPassword("testPassword");
		verify(settings).setTopics(List.of("testTopic0", "testTopic1"));
		verify(settings).setQos(2);
		verifyNoInteractions(this.notificator);
	}

	/**
	 * Diese Methode prüft den Click auf den Connect-Button.
	 */
	@Test
	public void testConnectButtonClick() {
		this.objectUnderTest.getConnectButton().click();

		verify(this.presenter).onConnect();
	}

	/**
	 * Diese Methode prüft den Click auf den Disconnect-Button.
	 */
	@Test
	public void testDisconnectButtonClick() {
		this.objectUnderTest.getDisconnectButton().click();

		verify(this.presenter).onDisconnect();
	}

	/**
	 * Diese Methode prüft {@link PuzzlesView#addRequest(CommonSolvePuzzleRequest)}.
	 */
	@Test
	public void testAddRequest() {
		CommonSolvePuzzleRequest request = mock(CommonSolvePuzzleRequest.class);

		when(request.getServerId()).thenReturn("testServerId");
		when(request.getRaetselId()).thenReturn(42L);

		this.objectUnderTest.addRequest(request);

		verify(this.puzzleMessageComponent).setHeadline("Rätsel von testServerId");
		verify(this.puzzleMessageComponent).setPuzzle(request);
		verify(this.puzzleMessageComponent).setServerId("testServerId");
		verify(this.puzzleMessageComponent).setPuzzleId(42L);
		assertEquals(List.of(this.puzzleMessageComponent), this.listOutputComponents());
	}

	/**
	 * Diese Methode prüft {@link PuzzlesView#addResponse(CommonSolvePuzzleResponse)}.
	 */
	@Test
	public void testAddResponse() {
		CommonSolvePuzzleResponse response = mock(CommonSolvePuzzleResponse.class);

		when(response.getServerId()).thenReturn("testServerId");
		when(response.getRaetselId()).thenReturn(42L);
		when(response.getTime()).thenReturn(13.0);

		this.objectUnderTest.addResponse(response);

		verify(this.puzzleMessageComponent).setHeadline("Lösung von testServerId");
		verify(this.puzzleMessageComponent).setPuzzle(response);
		verify(this.puzzleMessageComponent).setServerId("testServerId");
		verify(this.puzzleMessageComponent).setPuzzleId(42L);
		verify(this.puzzleMessageComponent).setTime(13.0);
		assertEquals(List.of(this.puzzleMessageComponent), this.listOutputComponents());
	}

	/**
	 * Diese Methode prüft {@link PuzzlesView#addNotification(String)}.
	 */
	@Test
	public void testAddNotification() {
		this.objectUnderTest.addNotification("testNotification0");
		this.objectUnderTest.addNotification("testNotification1");

		assertEquals(
				List.of("testNotification1", "testNotification0"),
				this.listOutputParagraphs());
	}

	/**
	 * Diese Methode listet alle Komponenten dem Output-Layout auf.
	 *
	 * @return die Liste der Textinhalte
	 */
	@Nonnull
	private List<Component> listOutputComponents() {
		return this.objectUnderTest.getOutputLayout().getChildren()
				.collect(Collectors.toList());
	}

	/**
	 * Diese Methode listet die Texte aller {@link Paragraph}s aus dem Output-Layout auf.
	 *
	 * @return die Liste der Textinhalte
	 */
	@Nonnull
	private List<String> listOutputParagraphs() {
		return this.objectUnderTest.getOutputLayout().getChildren()
				.filter(Paragraph.class::isInstance)
				.map(Paragraph.class::cast)
				.map(Paragraph::getText)
				.collect(Collectors.toList());
	}
}
