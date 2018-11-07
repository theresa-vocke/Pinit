package de.hdm.pinit.server;

import java.sql.Timestamp;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.pinit.server.db.PinboardMapper;
import de.hdm.pinit.server.db.UserMapper;
import de.hdm.pinit.shared.PinitService;
import de.hdm.pinit.shared.bo.Pinboard;
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
	 * direkt nach <code>GWT.create(Klassenname.class)</code> aufgerufen werden. Der
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
	 * Anlegen eines Pinboards. Dies führt zu einer Speicherung bzw. Ablage in der
	 * Datenbank.
	 */
	@Override
	public Pinboard createPinboard(User u) throws IllegalArgumentException {

		Pinboard p = new Pinboard();

		p.setOwnerId(u.getId());
		p.setCreateDate(new Timestamp(System.currentTimeMillis()));

		p.setId(1);

		return this.pMapper.insert(p);
	}

	/**
	 * Suche nach einer Pinnwand anhand des dazugehörigen Nutzers.
	 */
	@Override
	public Pinboard getPinboardByOwner(User u) throws IllegalArgumentException {

		return this.pMapper.findByOwner(u);
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
	 * Suche nach dem Nutzer diesem eine Pinnwand zugeordnet ist. Dies kann sich auf
	 * seine eigene Pinnwand beziehen oder jedoch auf eine Pinnwand eines anderen
	 * nutzers, die abonniert wurde.
	 */
	@Override
	public Subscription getSubscriptionByUser(int userId) throws IllegalArgumentException {

		return this.sMapper.findByUserId(userId);
	}
	
	/*
	 * ________________________________________________________________________
	 * ABSCHNITT Ende - Methoden für Subscription-Objekte
	 * ________________________________________________________________________
	 */

}
