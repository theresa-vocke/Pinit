package de.hdm.pinit.client.gui;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.view.client.ListDataProvider;

public class PinboardCellList<Pinboard> extends CellList<Pinboard> {

	public PinboardCellList(Cell<Pinboard> cell) {
		super(cell);
	}
	private ListDataProvider<Pinboard> pinboardDataProvider = null;
	
	private PinboardForm pinboardForm;

	/*
	 * 
	 */
	public ListDataProvider<Pinboard> getPinboardDataProvider() {
		return pinboardDataProvider;
	}

	public void setPinboardDataProvider(ListDataProvider<Pinboard> pinboardDataProvider) {
		this.pinboardDataProvider = pinboardDataProvider;
	}
	
	/*
	 * Hinzufügen und entfernen eines Pinboard-Elements aus der Celllist
	 */
	void addPinboard (Pinboard pinboard) {
		pinboardDataProvider.getList().add(pinboard);
	}
	
	void removePinboard (Pinboard pinboard) {
		pinboardDataProvider.getList().remove(pinboard);
	}
	
	/*
	 * 
	 */
	public void setPinboardForm(PinboardForm pf) {
		this.pinboardForm = pf;
	}
	

}
