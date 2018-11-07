package de.hdm.pinit.shared;

import java.sql.Timestamp;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.pinit.shared.bo.Pinboard;
import de.hdm.pinit.shared.bo.Subscription;
import de.hdm.pinit.shared.bo.User;

/**
 * Synchrone Schnittstelle für eine RPC-fähige Klasse. Erweitert das Interface
 * RemoteService, damit der RPC-Mechanismus verwendet werden kann. Führt alle
 * RRC-Methoden auf.
 * 
 * @RemoteServiceRelativePath: dem Dienst wird ein Standardpfad relativ zur
 *                             Basis-URL des Moduls zugeordnet.
 */
@RemoteServiceRelativePath("pinitservice")
public interface PinitService extends RemoteService {

	/**
	 * die init-Methode wird benötigt, um Instanzen der Mapper bei der
	 * Instantiierung innerhalb der PinitServiceImpl zu erstellen.
	 */	

	public void init() throws IllegalArgumentException;

	/**
	 *  User anlegen
	 */

	public User createUser(String nickname, String email) throws IllegalArgumentException;

	/**
	 * getUserById, um dann User auszulesen für den die Pinnwand angelegt werden soll
	 */

	public User getUserById(int id) throws IllegalArgumentException;

	/**
	 *  Pinnwand für diesen User erstellen (createPinboard)
	 */

	public Pinboard createPinboard(User u) throws IllegalArgumentException;
	
	/**
	 * getPinboardByOwner evtl. dass das Pinboard dann direkt angezeigt wird, wenn der Login abgeschlossen ist
	 */

	public Pinboard getPinboardByOwner(User u) throws IllegalArgumentException;


	public Subscription createSubscription(int userId, int pinboardId) throws IllegalArgumentException;

	Subscription getSubscriptionByUser(int userId) throws IllegalArgumentException;
	
}
