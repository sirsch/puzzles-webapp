package software.sirsch.sa4e.puzzlesWebapp;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
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

		assertEquals("tcp://localhost:1883", result);
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
		this.objectUnderTest.setBrokerUrl(null);

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
	 * Diese Methode prüft {@link Settings#getUsername()}.
	 */
	@Test
	public void testGetUsername() {
		String result;

		result = this.objectUnderTest.getUsername();

		assertNull(result);
	}

	/**
	 * Diese Methode prüft {@link Settings#setUsername(String)}.
	 */
	@Test
	public void testSetUsername() {
		this.objectUnderTest.setUsername("testUsername");

		assertEquals("testUsername", this.objectUnderTest.getUsername());
	}

	/**
	 * Diese Methode prüft {@link Settings#getPassword()}.
	 */
	@Test
	public void testGetPassword() {
		String result;

		result = this.objectUnderTest.getPassword();

		assertNull(result);
	}

	/**
	 * Diese Methode prüft {@link Settings#setPassword(String)}.
	 */
	@Test
	public void testSetPassword() {
		this.objectUnderTest.setPassword("testPassword");

		assertEquals("testPassword", this.objectUnderTest.getPassword());
	}

	/**
	 * Diese Methode prüft {@link Settings#getTopics()}.
	 */
	@Test
	public void testGetTopic() {
		List<String> result;

		result = this.objectUnderTest.getTopics();

		assertEquals(List.of("Zahlenraetsel", "Loesung"), result);
	}

	/**
	 * Diese Methode prüft {@link Settings#setTopics(List)}.
	 */
	@Test
	public void testSetTopic() {
		this.objectUnderTest.setTopics(List.of("testTopic0", "testTopic1"));

		assertEquals(List.of("testTopic0", "testTopic1"), this.objectUnderTest.getTopics());
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
