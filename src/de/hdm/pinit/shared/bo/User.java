package de.hdm.pinit.shared.bo;

public class User extends BusinessObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nickname = "";
	
	private String email = "";

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
}
