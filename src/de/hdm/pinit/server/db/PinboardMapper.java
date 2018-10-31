package de.hdm.pinit.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.pinit.shared.bo.Pinboard;
import de.hdm.pinit.shared.bo.User;
import de.hdm.pinit.server.db.DBConnection;

/**
 * Mapper-Klassen dienen dazu, Objekte auf eine relationale DB abzubilden.
 * Das Verfahren des Mappings verläuft bidirektional, das heißt, dass die
 * Objekte in Tupel umgewandelt werden und die Tupel in Objekte. Hier werden
 * Methoden definiert, die zum Beispiel zur Erstellung, Modifizierung und
 * Suche von Pinboard-Objekten verhelfen.
 */

/**
 * Genauso wie in der DBConnection, muss die Singleton-Eigenschaft gegeben sein,
 * das heißt, jede Mapper-Klasse wird nur einmal instanziiert. Soll eine Instanz
 * dieser Klasse erstellt werden, wird immer auf diese Klassenvariable
 * zurückgegriffen. Sie speichert die einzige Instanz dieser Klasse.
 */

public class PinboardMapper {

	private static PinboardMapper pinboardMapper = null;

	/**
	 * Die Zugriffsvariable auf den No-Argument Konstruktor muss protected oder
	 * private sein, damit eine Instanziierung von außen nicht zulässig ist. Es soll
	 * verhindert werden, dass über den new-Operator neue Instanzen dieser Klasse
	 * erzeugt werden.
	 */
	protected PinboardMapper() {
	}

	/**
	 * statische Methode, um eine Instanz von <code>PinboardMapper</code> zu
	 * erstellen. Wenn es schon eine Instanz dieser Klasse gibt, wird diese einfach
	 * zurückgegeben.
	 * 
	 * @return pinboardMapper
	 */
	public static PinboardMapper pinboardMapper() {
		if (pinboardMapper == null) {
			pinboardMapper = new PinboardMapper();
		}

		return pinboardMapper;
	}

	/**
	 * Einfügen eines Pinboard-Objektes in die DB
	 */

	public Pinboard insert(Pinboard p) {

		/*
		 * zuerst muss eine Verbindung zur DB hergestellt werden. Über den staischen
		 * Methodenaufruf wird die Verbindung zur DB geholt und in der Referenzvariable
		 * con gespeichert.
		 */
		Connection con = DBConnection.connection();

		try {
			/*
			 * Objekt von Statement erzeugen, damit die SQL Anweisungen an die DB gesendet
			 * werden können. Werden in der Referenzvariable stmt gespeichert.
			 */
			Statement stmt = con.createStatement();

			/*
			 * Das ResultSet stellt unsere Ergebnisse in einer Tabelle dar. ExecuteQuery
			 * gibt ein einzelnes ResultSet zurück. Gibt, wenn vorhanden, ein einzeiliges
			 * Ergebnis zurück. Hier überprüfen wir, was der momentan höchste
			 * Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM pinboard ");

			/*
			 * Next ist wie ein Iterator, setzt immer auf das aktuelle Element in der
			 * Ergebnistabelle und gibt true zurück, wenn ein nächstes Element existiert und
			 * false, wenn keins existiert.
			 */
			if (rs.next()) {

				/*
				 * p erhält den bisher maximalen, nun um 1 inkrementierten Primärschlüssel.
				 */
				p.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				/*
				 * Einfügeoperation. Damit die Objekte auf die Tabelle abgebildet werden. Wir
				 * holen uns über unser PinboardObjekt die Werte, die auf die Tabelle abgebildet
				 * werden sollen.
				 */
				stmt.executeUpdate("INSERT INTO pinboard (id, createdate, ownerid) " + "VALUES (" + p.getId() + "','"
						+ p.getCreateDate() + "','" + p.getOwnerId() + "')");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * Rückgabe des Pinboards, aus stilistischen Gründen.
		 */

		return p;
	}

	/**
	 * Löschen der Daten eines <code>Pinboard</code>-Objekts aus der Datenbank.
	 */
	public void delete(Pinboard p) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM pinboard " + "WHERE ownerid=" + p.getOwnerId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Auslesen aller Pinboard-Objekte mit gegebenem Nicknamen
	 */
	public Vector<Pinboard> findByOwnerId(int ownerId) {
		Connection con = DBConnection.connection();

		Vector<Pinboard> result = new Vector<Pinboard>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, createdate, ownerid " + "FROM pinboard "
					+ "WHERE ownerid LIKE '" + ownerId + "' ORDER BY ownerid");

			/*
			 * Für jeden Eintrag in der Ergebnistabelle wird ein Objekt abgebildet. Diese
			 * Ergebnisse werden in ein UserObjekt mit den Setter-Methoden rein gepackt.
			 */
			while (rs.next()) {
				Pinboard p = new Pinboard();
				p.setId(rs.getInt("id"));
				p.setCreateDate(rs.getTimestamp("createdate"));
				p.setOwnerId(rs.getInt("ownerid"));

				// Hinzufügen des neuen Objekts zum Ergebnisvektor
				result.addElement(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
		return result;
	}

}
