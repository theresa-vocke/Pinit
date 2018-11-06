package de.hdm.pinit.client.gui;

import com.google.gwt.core.shared.GWT;

import de.hdm.pinit.shared.PinitService;
import de.hdm.pinit.shared.PinitServiceAsync;

/**
 * 
 * @author Miescha
 *
 */

public class ClientSideSettings {

	/*
	 * RemoteServiceProxy zur Verbindungsaufbau mit dem serverseitigem Dienst namens
	 * Pinit Service. pinitService wird als Referenzvariable vom Typ
	 * PinitServiceAsync deklariert.
	 */
	private static PinitServiceAsync pinitService = null;

	/*
	 * Es wird die statische Methode getPinitService definiert daraufhin in der
	 * EntryPoint Klasse mit ClientsideSettings.getPinitService aufgerufen.
	 * -
	 * Diese Methode wird im Client in Pinit.java aufgerufen 
	 */
	public static PinitServiceAsync getPinitService() {

		/*
		 * Prüft ob es bereits ein vorhandes ProxyObjekt gibt, falls nicht wird eine
		 * erstellt
		 */
		if (pinitService == null) {

			/*
			 * GWT.create ermöglicht Instantiierung des synchronen Interfaces. Ist das Proxy
			 * Objekt erstellt, ist eine Verbindung zwischen Client und Server möglich
			 */
			pinitService = GWT.create(PinitService.class);
		}
		
		// Rückgabe der eindeutigen Instanz des einzigen Proxys
		return pinitService;
	}

}
