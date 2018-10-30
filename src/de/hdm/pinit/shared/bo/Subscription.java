package de.hdm.pinit.shared.bo;

public class Subscription extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private int userId;

private int pinboardId;

public int getUserId() {
	return userId;
}

public void setUserId(int userId) {
	this.userId = userId;
}

public int getPinboardId() {
	return pinboardId;
}

public void setPinboardId(int pinboardId) {
	this.pinboardId = pinboardId;
}

}
