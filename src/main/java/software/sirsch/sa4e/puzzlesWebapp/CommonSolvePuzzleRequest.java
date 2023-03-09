package software.sirsch.sa4e.puzzlesWebapp;

import java.util.List;

import javax.annotation.CheckForNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Diese Klasse bildet das Austauschdatenmodell für eine Lösungsanfrage ab.
 *
 * @author sirsch
 * @since 26.01.2023
 */
public class CommonSolvePuzzleRequest {

	/**
	 * Dieses Feld kann die ID des Servers enthalten.
	 */
	@CheckForNull
	private String serverId;

	/**
	 * Dieses Feld kann die ID des Rätsels enthalten.
	 */
	@CheckForNull
	private Long raetselId;

	/**
	 * Dieses Feld kann die erste Zeile enthalten.
	 */
	@CheckForNull
	private List<String> row1;

	/**
	 * Dieses Feld kann die zweite Zeile enthalten.
	 */
	@CheckForNull
	private List<String> row2;

	/**
	 * Dieses Feld kann die dritte Zeile enthalten.
	 */
	@CheckForNull
	private List<String> row3;

	/**
	 * Diese Methode gibt die ID des Servers zurück.
	 *
	 * @return die ID, falls vorhanden
	 */
	@JsonProperty("server_id")
	@CheckForNull
	public String getServerId() {
		return this.serverId;
	}

	/**
	 * Diese Methode legt die ID des Servers fest.
	 *
	 * @param serverId die zu setzende ID
	 */
	@JsonProperty("server_id")
	public void setServerId(@CheckForNull final String serverId) {
		this.serverId = serverId;
	}

	/**
	 * Diese Methode gibt die ID des Rätsels zurück.
	 *
	 * @return die ID, falls vorhanden
	 */
	@JsonProperty("raetsel_id")
	@CheckForNull
	public Long getRaetselId() {
		return this.raetselId;
	}

	/**
	 * Diese Methode legt die ID des Rätsels fest.
	 *
	 * @param raetselId die zu setzende ID
	 */
	@JsonProperty("raetsel_id")
	public void setRaetselId(@CheckForNull final Long raetselId) {
		this.raetselId = raetselId;
	}

	/**
	 * Diese Methode gibt die erste Zeile zurück.
	 *
	 * @return die Zeile, falls vorhanden
	 */
	@CheckForNull
	public List<String> getRow1() {
		return this.row1;
	}

	/**
	 * Diese Methode legt die erste Zeile fest.
	 *
	 * @param row1 die zu setzende Zeile
	 */
	public void setRow1(@CheckForNull final List<String> row1) {
		this.row1 = row1;
	}

	/**
	 * Diese Methode gibt die zweite Zeile zurück.
	 *
	 * @return die Zeile, falls vorhanden
	 */
	@CheckForNull
	public List<String> getRow2() {
		return this.row2;
	}

	/**
	 * Diese Methode legt die zweite Zeile fest.
	 *
	 * @param row2 die zu setzende Zeile
	 */
	public void setRow2(@CheckForNull final List<String> row2) {
		this.row2 = row2;
	}

	/**
	 * Diese Methode gibt die dritte Zeile zurück.
	 *
	 * @return die Zeile, falls vorhanden
	 */
	@CheckForNull
	public List<String> getRow3() {
		return this.row3;
	}

	/**
	 * Diese Methode legt die dritte Zeile fest.
	 *
	 * @param row3 die zu setzende Zeile
	 */
	public void setRow3(@CheckForNull final List<String> row3) {
		this.row3 = row3;
	}
}