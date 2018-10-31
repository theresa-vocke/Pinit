package de.hdm.pinit.shared.bo;

public class User extends BusinessObject {

	/**
	 * <code>serialVersionUID</code> Ist eine Zahl vom Typ long, die zur eindeutigen Identifikation der Version
	 * einer serialisierbaren Klasse dient. Die Versionsnummer wird bei der
	 * Serialisierung automatisch jeder Klasse hinzugefügt, die das Interface
	 * Serializable implementiert.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Jeder Nutzer erhält einen Nicknamen 
	 */
	private String nickname = "";
	
	/**
	 * Jeder Nutzer registriert sich mit einer Email 
	 */
	private String email = "";
	
	/**
	 * Jeder Nutzer erhält hat einen Vornamen 
	 */
	private String prename = "";
	
	/**
	 * Jeder Nutzer erhält hat einen Nachnamen 
	 */
	private String surname = "";

	
	/**Getter- und Setter-Methoden der Attribute
	 * 
	 * 
	 */
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrename() {
		return prename;
	}

	public void setPrename(String prename) {
		this.prename = prename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	/**
	   * Erzeugen einer einfachen textuellen Darstellung der jeweiligen Instanz.
	   * Diese besteht aus dem Text, der durch die <code>toString()</code>-Methode
	   * der Superklasse erzeugt wird, ergÃ¤nzt durch den Nicknamen des 
	   * jeweiligen Nutzers.
	   * 
	   * @author Thies, Miescha
	   */
	  @Override
	public String toString() {
	    return super.toString() + " " + this.nickname;
	  }

	
	
}
