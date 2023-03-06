package software.sirsch.sa4e.puzzlesWebapp;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
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
	 * Dieses Feld soll das zu testende Objekt enthalten.
	 */
	private PuzzlesView objectUnderTest;

	/**
	 * Diese Methode bereitet die Testumgebung für jeden Testfall vor.
	 */
	@BeforeEach
	public void setUp() {
		this.presenter = mock(PuzzlesPresenter.class);
		this.notificator = mock(Consumer.class);
		this.uiProvider = mock(Function.class);

		this.objectUnderTest = new PuzzlesView(this.presenter, this.notificator, this.uiProvider);
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
	 * Diese Methode prüft {@link PuzzlesView#addNotification(String)}.
	 */
	@Test
	public void testAddNotification() {
	}
}