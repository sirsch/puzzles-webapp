package software.sirsch.sa4e.puzzlesWebapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Diese Klasse stellt Tests für {@link Settings} bereit.
 *
 * @author sirsch
 * @since 04.03.2023
 */
public class SettingsTest {

	/**
	 * Dieses Feld soll das zu testende Objekt enthalten.
	 */
	private Settings objectUnderTest;

	/**
	 * Diese Methode bereitet die Testumgebung für jeden Testfall vor.
	 */
	@BeforeEach
	public void setUp() {
		this.objectUnderTest = new Settings();
	}

	/**
	 * Diese Methode prüft {@link Settings#getBrokerUrl()}.
	 */
	@Test
	public void testGetBrokerUrl() {
		String result;

		result = this.objectUnderTest.getBrokerUrl();

		assertNull(result);
	}

	/**
	 * Diese Methode prüft {@link Settings#setBrokerUrl(String)}.
	 */
	@Test
	public void testSetBrokerUrl() {
		this.objectUnderTest.setBrokerUrl("test://broker/url");

		assertEquals("test://broker/url", this.objectUnderTest.getBrokerUrl());
	}

	/**
	 * Diese Methode prüft {@link Settings#requireBrokerUrl()}.
	 */
	@Test
	public void testRequireBrokerUrl() {
		String result;

		this.objectUnderTest.setBrokerUrl("test://broker/url");

		result = this.objectUnderTest.requireBrokerUrl();

		assertEquals("test://broker/url", result);
	}

	/**
	 * Diese Methode prüft {@link Settings#requireBrokerUrl()}, wenn der Wert nicht gesetzt ist.
	 */
	@Test
	public void testRequireBrokerUrlNull() {
		assertThrows(IllegalStateException.class, () -> this.objectUnderTest.requireBrokerUrl());
	}

	/**
	 * Diese Methode prüft {@link Settings#getClientId()}.
	 */
	@Test
	public void testGetClientId() {
		String result;

		result = this.objectUnderTest.getClientId();

		assertNotNull(result);
		assertTrue(startsWith(result, "puzzles-webapp_"));
		assertEquals(23, result.length());
	}

	/**
	 * Diese Methode prüft {@link Settings#setClientId(String)}.
	 */
	@Test
	public void testSetClientId() {
		this.objectUnderTest.setClientId("testClientId");

		assertEquals("testClientId", this.objectUnderTest.getClientId());
	}

	/**
	 * Diese Methode prüft {@link Settings#getTopic()}.
	 */
	@Test
	public void testGetTopic() {
		String result;

		result = this.objectUnderTest.getTopic();

		assertNull(result);
	}

	/**
	 * Diese Methode prüft {@link Settings#setTopic(String)}.
	 */
	@Test
	public void testSetTopic() {
		this.objectUnderTest.setTopic("testTopic");

		assertEquals("testTopic", this.objectUnderTest.getTopic());
	}

	/**
	 * Diese Methode prüft {@link Settings#requireTopic()}.
	 */
	@Test
	public void testRequireTopic() {
		String result;

		this.objectUnderTest.setTopic("testTopic");

		result = this.objectUnderTest.requireTopic();

		assertEquals("testTopic", result);
	}

	/**
	 * Diese Methode prüft {@link Settings#requireTopic()}, wenn der Wert nicht gesetzt ist.
	 */
	@Test
	public void testRequireTopicNull() {
		assertThrows(IllegalStateException.class, () -> this.objectUnderTest.requireTopic());
	}

	/**
	 * Diese Methode prüft {@link Settings#getQos()}.
	 */
	@Test
	public void testGetQos() {
		Integer result;

		result = this.objectUnderTest.getQos();

		assertEquals(2, result);
	}

	/**
	 * Diese Methode prüft {@link Settings#setQos(Integer)}.
	 */
	@Test
	public void testSetQos() {
		this.objectUnderTest.setQos(1);

		assertEquals(1, this.objectUnderTest.getQos());
	}
}
