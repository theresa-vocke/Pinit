package de.hdm.pinit.shared.bo;

public class Pinboard extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		

	/**
	 * Owner der Pinwand
	 */
		
	private User user;
	

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
}
