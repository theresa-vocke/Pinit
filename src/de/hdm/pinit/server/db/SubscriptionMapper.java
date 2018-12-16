package de.hdm.pinit.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.pinit.shared.bo.Subscription;

public class SubscriptionMapper {

	private static SubscriptionMapper subscriptionMapper = null;

	/**
	 * Die Zugriffsvariable auf den No-Argument Konstruktor muss protected oder
	 * private sein, damit eine Instanziierung von außen nicht zulässig ist. Es
	 * soll verhindert werden, dass über den new-Operator neue Instanzen dieser
	 * Klasse erzeugt werden.
	 */
	protected SubscriptionMapper() {
	}

	/**
	 * statische Methode, um eine Instanz von <code>SubscriptionMapper</code> zu
	 * erstellen. Wenn es schon eine Instanz dieser Klasse gibt, wird diese
	 * einfach zurückgegeben.
	 * 
	 * @return subscriptionMapper
	 */
	public static SubscriptionMapper subscriptionMapper() {
		if (subscriptionMapper == null) {
			subscriptionMapper = new SubscriptionMapper();
		}

		return subscriptionMapper;
	}

	/**
	 * Einfügen eines Subscription-Objektes in die DB
	 */

	public Subscription insert(Subscription s) {

		/*
		 * zuerst muss eine Verbindung zur DB hergestellt werden. Über den
		 * staischen Methodenaufruf wird die Verbindung zur DB geholt und in der
		 * Referenzvariable con gespeichert.
		 */
		Connection con = DBConnection.connection();

		try {
			/*
			 * Objekt von Statement erzeugen, damit die SQL Anweisungen an die
			 * DB gesendet werden können. Werden in der Referenzvariable stmt
			 * gespeichert.
			 */
			Statement stmt = con.createStatement();

			/*
			 * Das ResultSet stellt unsere Ergebnisse in einer Tabelle dar.
			 * ExecuteQuery gibt ein einzelnes ResultSet zurück. Gibt, wenn
			 * vorhanden, ein einzeiliges Ergebnis zurück. Hier überprüfen wir,
			 * was der momentan höchste Primärschlüsselwert ist.
			 */
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM subscription ");

			/*
			 * Next ist wie ein Iterator, setzt immer auf das aktuelle Element
			 * in der Ergebnistabelle und gibt true zurück, wenn ein nächstes
			 * Element existiert und false, wenn keins existiert.
			 */
			if (rs.next()) {

				/*
				 * s erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				s.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				/*
				 * Einfügeoperation. Damit die Objekte auf die Tabelle
				 * abgebildet werden. Wir holen uns über unser
				 * SubscriptionObjekt die Werte, die auf die Tabelle abgebildet
				 * werden sollen.
				 */
				stmt.executeUpdate("INSERT INTO subscription (nutzerid, pinboardid) " + "VALUES (" + s.getUserId()
						+ "','" + s.getPinboardId() + "')");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * Rückgabe der Subscription, aus stilistischen Gründen.
		 */

		return s;
	}

	/**
	 * Löschen der Daten eines <code>Subscription</code>-Objekts aus der
	 * Datenbank.
	 */
	public void delete(Subscription s) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM `subscription` WHERE `subscription`.`userid` = "+s.getUserId()+" AND `subscription`.`pinboardid` = "+s.getPinboardId());
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Auslesen aller <code>Subscription</code>-Objekte nach Nutzern
	 */
	public Vector<Subscription> findByUserId(int userId) {
		Connection con = DBConnection.connection();

		Vector<Subscription> sub = new Vector<Subscription>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT userid, pinboardid " + "FROM subscription " + "WHERE userid=" + userId);

			/*
			 * Für jeden Eintrag in der Ergebnistabelle wird ein Objekt
			 * abgebildet. Diese Ergebnisse werden in ein Subscription- Objekt
			 * mit den Setter-Methoden rein gepackt.
			 */
			while (rs.next()) {
				Subscription s = new Subscription();
				s.setUserId(rs.getInt("userid"));
				s.setPinboardId(rs.getInt("pinboardid"));

				sub.add(s);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return sub;
	}

	/**
	 * Auslesen aller <code>Subscription</code>-Objekte nach Pinnwänden
	 */
	public Vector<Subscription> findByPinboardId(int pinboardId) {
		Connection con = DBConnection.connection();

		Vector<Subscription> sub = new Vector<Subscription>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT userid, pinboardid " + "FROM subscription " + "WHERE pinboardid LIKE '" + pinboardId);

			/*
			 * Für jeden Eintrag in der Ergebnistabelle wird ein Objekt
			 * abgebildet. Diese Ergebnisse werden in ein Subscription- Objekt
			 * mit den Setter-Methoden rein gepackt.
			 */
			while (rs.next()) {
				Subscription s = new Subscription();
				s.setUserId(rs.getInt("userid"));
				s.setPinboardId(rs.getInt("pinboardid"));

				sub.add(s);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return sub;
	}

}
