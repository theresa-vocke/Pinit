package de.hdm.pinit.shared.bo;

public class Comment extends Textdocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private Posting posting;

public Posting getPosting() {
	return posting;
}

public void setPosting(Posting posting) {
	this.posting = posting;
}


}
