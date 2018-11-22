package de.hdm.pinit.client.gui;

import java.util.logging.Logger;

import com.google.gwt.core.shared.GWT;

import de.hdm.pinit.shared.CommonSettings;
import de.hdm.pinit.shared.LoginService;
import de.hdm.pinit.shared.LoginServiceAsync;
import de.hdm.pinit.shared.PinitService;
import de.hdm.pinit.shared.PinitServiceAsync;

/**
 * 
 * @author Miescha
 *
 */

public class ClientSideSettings extends CommonSettings{

	/*
	 * RemoteServiceProxy zur Verbindungsaufbau mit dem serverseitigem Dienst namens
	 * Pinit Service. pinitService wird als Referenzvariable vom Typ
	 * PinitServiceAsync deklariert.
	 */
	private static PinitServiceAsync pinitService = null;
	
	private static LoginServiceAsync loginService = null; 

	/**
	 * Name des Client-seitigen Loggers.
	 */
	private static final String LOGGER_NAME = "SocialMedia Web Client";
	
	/**
	 * Instanz des Client-seitigen Loggers.
	 */
	
	private static final Logger log = Logger.getLogger(LOGGER_NAME);
	
	/**
	 * Auslesen des applikationsweiten (Client-seitig) zentralen Loggers.
	 * @return die Logger-Instanz für die Server-Seite.
	 */
	
	public static Logger getLogger(){
		return log;
	}
	
	
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

	/**
	 * Durch die Methode wird die LoginService erstellt, sofern diese noch nicht besteht.
	 * Bei erneutem Aufruf der Methode wird das bereits angelegte Objekt zurückgegeben.
	 * 
	 * @return eindeutige Instanz des Typs <code>LoginServiceAsync</code>
	 */
	public static LoginServiceAsync getLoginService(){
		if(loginService == null){
			loginService = GWT.create(LoginService.class);
		}
		return loginService;
	}
	
}
