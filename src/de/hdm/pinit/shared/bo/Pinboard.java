package de.hdm.pinit.shared.bo;

public class Pinboard extends BusinessObject {

	/**
	 * <code>SerialVersionUID</code> ist eine Zahl vom Typ long, die zur
	 * eindeutigen Identifikation der Version einer serialisierbaren Klasse
	 * dient. Die Versionsnummer wird bei der Serialisierung automatisch jeder
	 * Klasse hinzugef�gt, die das Interface Serializable implementiert
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Jede Pinnwand hat einen Owner, der �ber die <code>ownerId</code>
	 * identifiziert werden kann.
	 */
	
	
	private User user;

	private int ownerId;

	/**
	 * Getter- und Setter-Methoden
	 */
		
	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int userId) {
		this.ownerId = userId;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
