package de.hdm.pinit.shared.bo;

public class Pinboard extends BusinessObject {

	/**
	 * <code>SerialVersionUID</code> ist eine Zahl vom Typ long, die zur
	 * eindeutigen Identifikation der Version einer serialisierbaren Klasse
	 * dient. Die Versionsnummer wird bei der Serialisierung automatisch jeder
	 * Klasse hinzugefügt, die das Interface Serializable implementiert
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Jede Pinnwand hat einen Owner, der über die <code>ownerId</code>
	 * identifiziert werden kann.
	 */

	private int ownerId;

	/**
	 * Getter- und Setter-Methoden
	 */
		
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerid) {
		this.ownerId = ownerId;
	}
}
