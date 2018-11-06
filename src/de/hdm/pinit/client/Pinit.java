package de.hdm.pinit.client;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.pinit.client.gui.ClientSideSettings;
import de.hdm.pinit.client.gui.PinboardCell;
import de.hdm.pinit.client.gui.PinboardForm;
import de.hdm.pinit.client.gui.PostingForm;
import de.hdm.pinit.client.gui.SubscriptionButton;
import de.hdm.pinit.shared.PinitService;
import de.hdm.pinit.shared.PinitServiceAsync;
import de.hdm.pinit.shared.bo.Pinboard;

/**
 * Entry point classes define <code>onModuleLoad()</code>. Aufgabe der
 * Entrypoint
 */
public class Pinit implements EntryPoint {

	// Deklarieren einer Variable mit dem Referenzdatentyp PinitServiceAsync.
	PinitServiceAsync pinitService = null;

	/*
	 * <code>onModuleLoad()</code> muss implementiert werden, da es Bestandteil des
	 * Interfaces EntryPoint ist und wir diese implementieren
	 */
	@Override
	public void onModuleLoad() {
		/*
		 * Prüfung ob die Referenzvariable pinitService bereits mit einem Wert belegt
		 * ist. Ist dies nicht der Fall, so wird die Methode getBankverwaltung aus der
		 * Klasse ClientsideSetting aufgerufen. Das Ergebnis der Methode wird dann
		 * pinitService zugewiesen.
		 */
		if (pinitService == null) {
			pinitService = ClientSideSettings.getPinitService();
		}

		// irg Beschreibung
		
		PinboardForm pif = new PinboardForm();
		PostingForm pof = new PostingForm();
		SubscriptionButton sb = new SubscriptionButton();
		CellList<Pinboard> clp = new CellList<Pinboard>(new PinboardCell());
	
		
	}

}
