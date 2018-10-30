package de.hdm.pinit.shared.bo;

public abstract class Textdocument extends BusinessObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String text = "";
	
	private Pinboard pinboard;
	
	private int ownerId;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Pinboard getPinboard() {
		return pinboard;
	}

	public void setPinboard(Pinboard pinboard) {
		this.pinboard = pinboard;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
}
