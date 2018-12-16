package de.hdm.pinit.shared.bo;

import java.util.Date;

public class Posting extends Textdocument {

	private static final long serialVersionUID = 1L;

	private Date createdate = new Date();
		
	private String text = null;
	
	private int ownerId;
	
	private int pinboardId;

	public Date getCreatedate() {
		return createdate;
	}

	public int getPinboardId() {
		return pinboardId;
	}

	public void setPinboardId(int pinboardId) {
		this.pinboardId = pinboardId;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	

	
}
