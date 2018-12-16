package de.hdm.pinit.server;

import java.sql.Timestamp;
import java.util.Vector;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.pinit.server.db.PinboardMapper;
import de.hdm.pinit.server.db.PostingMapper;
import de.hdm.pinit.server.db.SubscriptionMapper;
import de.hdm.pinit.server.db.UserMapper;
import de.hdm.pinit.shared.PinitService;
import de.hdm.pinit.shared.bo.Pinboard;
import de.hdm.pinit.shared.bo.Posting;
import de.hdm.pinit.shared.bo.Subscription;
import de.hdm.pinit.shared.bo.User;

/**
 * Die serversetigie Implementation des RPC Services. Die Impl Klasse stellt 
 * unser serverseitiges Servlet dar, durch die Erweiterung des 
 * RemoteServiceServlets. Das zu erweiternde RemoteServiceServlet stellt die 
 * Basis zur Verfügung, dass der RPC-Mechanismus verwendet werden kann.  
 * Sie enthält sämliche Methoden die die Applikationslogik definieren. Hier 
 * werden die Funktionen und Zusammenhänge unserer Daten und Abläufe organisiert. 
 * Gemeinsam mit dem synchronen Interface bildet PinitServiceImpl die 
 * serverseitige Sicht der Applikationslogik ab. 
 * 
 */

/**
 * Compiler erwartet eine SerialVersionUniqueID, für alle serialisierbaren
 * Klassen. Wird keine Default-ID vergeben, so unterdrückt die Annotation die
 * Warnung. RemoteServiceServlet implementiert das Interface IsSerializable.
 * 
 * @author Miescha
 *
 */
@SuppressWarnings("serial")
public class PinitServiceImpl extends RemoteServiceServlet implements PinitService {

	/**
	 * Referenz auf den DatenbankMapper, der Userobjekte mit der Datenbank
	 * abgleicht.
	 */
	private UserMapper uMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Pinboardobjekte mit der Datenbank
	 * abgleicht.
	 */
	private PinboardMapper pMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Pinboardobjekte mit der Datenbank
	 * abgleicht.
	 */
	private SubscriptionMapper sMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Postingobjekte mit der Datenbank
	 * abgleicht.
	 */
	private PostingMapper poMapper = null;

	/*
	 * ________________________________________________________________________
	 * ABSCHNITT Start - Initialisierung
	 * ________________________________________________________________________
	 */

	/**
	 * <p>
	 * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
	 * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu ist
	 * ein solcher No-Argument-Konstruktor anzulegen.Bei diesem Aufruf können dem
	 * Konstruktor keine Werte übergebene werden, weshalb die Init-Methode die
	 * Initalisierung der Mapperinstanzen ausführen muss. Immer wenn eine Instanz
	 * der ServletImplementierungsklasse instanziiert wird, muss die init-Methode
	 * direkt nach <code>GWT.create(Klassenname.class)</code> aufgerufen werden. Bei
	 * der ersten Erzeugung des Proxy beim Laden der onModuleLoad() wird die Instanz
	 * des Servlets erzeugt, worüber zunächst das Servlet zum ersten Mal geladen
	 * wird. Wird das Servlet über eine HTTP-Anfrage angesprochen, so wird diese
	 * einzige konfigurierte Instanz des Servlets zurückgegeben. Der
	 * NoArgumentConstructors wird lediglich dahingehend erweitert, die
	 * Ausnahmebehandlung durchführen zu können.
	 */
	public PinitServiceImpl() throws IllegalArgumentException {
	}

	@Override
	public void init() throws IllegalArgumentException {

		this.uMapper = UserMapper.userMapper();
		this.pMapper = PinboardMapper.pinboardMapper();
		this.sMapper = SubscriptionMapper.subscriptionMapper();
		this.poMapper = PostingMapper.postingMapper();
	}
	/*
	 * ________________________________________________________________________
	 * ABSCHNITT Ende - Initialisierung
	 * ________________________________________________________________________
	 */

	/*
	 * ________________________________________________________________________
	 * ABSCHNITT Start - Methoden für User-Objekte
	 * ________________________________________________________________________
	 */

	/**
	 * Anlegen eines Nutzers. Dies führt zu einer Speicherung bzw. Ablage in der
	 * Datenbank. Wenn ein Nutzer angelegt wird, wird eine Pinnwand erstellt und ihm
	 * zugeordnet.
	 */
	@Override
	public User createUser(String nickname, String email) throws IllegalArgumentException {
		User u = new User();

		u.setNickname(nickname);
		u.setEmail(email);
		u.setCreateDate(new Timestamp(System.currentTimeMillis()));

		/*
		 * Setzen einer vorläufigen ID, die in der DB nachträglich richtig zugeordnet
		 * wird.
		 */
		u.setId(1);

		// U-Objekt in der Db speichern.
		return this.uMapper.insert(u);
	}

	/**
	 * Ein Nutzer wird anhand seiner ID gesucht.
	 */
	@Override
	public User getUserById(int id) throws IllegalArgumentException {

		return this.uMapper.findById(id);
	}

	/**
	 * Alle Nutzer werden ausgelesen und in einem Vektor zurückgegeben.
	 */

	@Override
	public Vector<User> getAllUser() {
		Vector<User> u = new Vector<User>();
		u = this.uMapper.findAll();

		return u;
	}

	/**
	 * Anhand der übergebenen E-Mail wird überprüft, ob sich der Nutzer mit der
	 * entsprechenden Email bereits registriert hat.
	 */

	@Override
	public User checkUser(String email) throws IllegalArgumentException {
		/*
		 * Leerer Vektor, welchem alle in der DB gespeicherten Nutzer zugewiesen werden.
		 */
		Vector<User> u = getAllUser();
		/*
		 * Jeder einzelne Nutzer innerhalb der Liste wird überprüft.
		 */
		for (User user : u) {
			/*
			 * Dies geschieht so lange, bis die übergebene EMail mit der eines Nutzers (im
			 * Vektor) übereinstimmt.
			 */
			if (email.equals(user.getEmail())) {
				// Der User wird zurückgegeben.
				return user;
			}
		}
		/*
		 * Falls nicht, wird nichts zurück gegeben und der aktuelle Nutzer muss sich
		 * dann registrieren.
		 */
		return null;
	}

	/**
	 * Für einen User wird anhand seiner ID überprüft, welche anderen User (mit
	 * deren Pinnwände) er abonniert hat.
	 */
	@Override
	public Vector<User> getAllSubscriptionsByUser(int id) throws IllegalArgumentException {
		/*
		 * leerer Vektor wird angelegt, in welchen dann alle abonnierten User
		 * gespeichert werden.
		 */
		Vector<User> u = new Vector<User>();
		/*
		 * leerer Vektor wird angelegt, in welchen alle Pinnwände gespeicher werden, die
		 * der User abonniert hat.
		 */
		Vector<Pinboard> p = new Vector<Pinboard>();
		/*
		 * Vektor wird angelegt, in welchen alle einzelnen Abos des Users direkt
		 * gespeichert werden. Anhand eines Nutzers mit übergebener userID.
		 */
		Vector<Subscription> s = this.getSubscriptionByUser(id);

		/*
		 * Zu jedem einzeln gespeicherte Abo im Subscription Vektor wird die dazu
		 * gehörende Pinnwand geholt. Für jedes Exemplar in diesem Vektor wird die
		 * Schleife ausgeführt.
		 */
		for (Subscription subscription : s) {
			/*
			 * Zu dem zuvor angelegten leeren Pinnwand Vektor werden nun die abonnierten
			 * Pinnwände hinzugefügt.
			 */
			p.add(getPinboardBySubscription(subscription));
		}
		/*
		 * Zu jedem einzeln gespeicherte Pinboard-Objekt im Pinnwand Vektor wird der
		 * dazu gehörige User geholt. Für jedes Exemplar in diesem Vektor wird die
		 * Schleife ausgeführt.
		 */
		for (Pinboard pinboard : p) {
			/*
			 * Zu dem zuvor angelegten leeren User Vektor werden nun die entsprechenden
			 * Eigentümer Pinnwände ausgelesen und dem Vektor hinzugefügt.
			 */
			u.add(getOwnerByPinboard(pinboard));
		}
		/*
		 * Alle Owner der abonnierten Pinnwände, welcher der übergebene Nutzer abonniert
		 * hat, werden als Vektor zurück gegeben.
		 */
		return u;
	}

	/**
	 * Der Eigentümer einer Pinnwand wird ausgelesen.
	 */
	@Override
	public User getOwnerByPinboard(Pinboard p) throws IllegalArgumentException {
		User u = new User();
		u = this.getUserById(p.getOwnerId());
		return u;
	}

	@Override
	public User getUserByNickname(String nickname) throws IllegalArgumentException {
		return this.uMapper.findByNickname(nickname);
	}

	/*
	 * ________________________________________________________________________
	 * ABSCHNITT Ende - Methoden für User-Objekte
	 * ________________________________________________________________________
	 */

	/*
	 * ________________________________________________________________________
	 * ABSCHNITT Start - Methoden für Pinboard-Objekte
	 * ________________________________________________________________________
	 */

	/**
	 * Anlegen eines Pinboards bei Registrierung des Nutzers. Dies führt zu einer
	 * Speicherung bzw. Ablage in der Datenbank.
	 */
	@Override
	public Pinboard createPinboard(int ownerId) throws IllegalArgumentException {

		Pinboard p = new Pinboard();

		p.setOwnerId(ownerId);
		p.setCreateDate(new Timestamp(System.currentTimeMillis()));

		p.setId(1);

		return this.pMapper.insert(p);
	}

	/**
	 * Suche nach einer Pinnwand anhand des dazugehörigen Nutzers.
	 */
	@Override
	public Pinboard getPinboardByOwner(int userId) throws IllegalArgumentException {
		init();
		return this.pMapper.findByOwner(userId);
	}

	/**
	 * Eine Pinnwand wird anhand eines übergebenen Abo-Objektes ausgelesen.
	 */
	@Override
	public Pinboard getPinboardBySubscription(Subscription s) throws IllegalArgumentException {
		init();
		/*
		 * Über das übergebene Abo-Objekt wird auf die Pinnwand-ID zugegriffen und
		 * darüber wird ein tatsächlichen Pinnwand-Objekt in der DB gesucht.
		 */
		Pinboard p = pMapper.findById(s.getPinboardId());
		return p;
	}

	/*
	 * ________________________________________________________________________
	 * ABSCHNITT Ende - Methoden für Pinboard-Objekte
	 * ________________________________________________________________________
	 */

	/*
	 * ________________________________________________________________________
	 * ABSCHNITT Start - Methoden für Subscription-Objekte
	 * ________________________________________________________________________
	 */

	/**
	 * Anlegen eines Abonnements. Dies führt zu einer Speicherung bzw. Ablage in der
	 * Datenbank. Einem Nutzer wird eine Pinboard zugewiesen. Die kann sich auf
	 * seine eigene Pinnwand beziehen oder jedoch auf eine Pinnwand eines anderen
	 * Nutzers, die abonniert wird.
	 */
	@Override
	public Subscription createSubscription(int userId, int pinboardId) throws IllegalArgumentException {

		Subscription s = new Subscription();

		s.setUserId(userId);
		s.setPinboardId(pinboardId);
		// s.setCreateDate(new Timestamp(System.currentTimeMillis()));

		s.setId(1);

		return this.sMapper.insert(s);
	}

	/**
	 * Suche nach dem Nutzer, dem eine Pinnwand zugeordnet ist. Dies kann sich auf
	 * seine eigene Pinnwand beziehen oder jedoch auf eine Pinnwand eines anderen
	 * nutzers, die abonniert wurde.
	 */
	@Override
	public Vector<Subscription> getSubscriptionByUser(int userId) throws IllegalArgumentException {

		return this.sMapper.findByUserId(userId);
	}

	@Override
	public void deleteSubscription(int userId, String nickname) throws IllegalArgumentException {
		User u = getUserByNickname(nickname);
		Pinboard p = getPinboardByOwner(u.getId());
		Subscription s = new Subscription();
		s.setUserId(userId);
		s.setPinboardId(p.getId());
		s.setId(1);
		this.sMapper.delete(s);

		return;
	}

	/*
	 * ________________________________________________________________________
	 * ABSCHNITT Ende - Methoden für Subscription-Objekte
	 * ________________________________________________________________________
	 */

	/*
	 * ________________________________________________________________________
	 * ABSCHNITT Start - Methoden für Pinboard-Objekte
	 * ________________________________________________________________________
	 */

	/**
	 * Anlegen eines Postings, wobei ein Nutzer hinterlegt wird. Dies führt zu einer
	 * Speicherung bzw. Ablage in der Datenbank.
	 */
	@Override
	public Posting createPosting(int pinboardId, String text) {

		Posting p = new Posting();

		p.setPinboardId(pinboardId);
		p.setText(text);
		p.setCreateDate(new Timestamp(System.currentTimeMillis()));

		p.setId(1);

		return poMapper.insert(p);
	}

	/**
	 * Auslesen aller Beiträge die einer bestimmten Pinboard zugeordnet sind.
	 */
	@Override
	public Vector<Posting> getPostingsByPinboard(int id) {

		return this.poMapper.findPostingsByPinboard(id);
	}

	/**
	 * Alle Beiträge einer Pinnwand anzeigen lassen, wovon der Nutzer der Eigentümer
	 * ist.
	 */
	@Override
	public Vector<Posting> getPostingsByPinboardOwner(int ownerId) {

		Pinboard p = this.getPinboardByOwner(ownerId);
		Vector<Posting> po = this.getPostingsByPinboard(p.getId());
		return po;
	}

	/**
	 * Einen bestimmten Beitrag einer Pinnwand löschen.
	 * 
	 */
	@Override
	public void deletePosting(Posting p) {

		this.poMapper.delete(p);
	}

	/*
	 * ________________________________________________________________________
	 * ABSCHNITT Ende - Methoden für Subscription-Objekte
	 * ________________________________________________________________________
	 */
}
