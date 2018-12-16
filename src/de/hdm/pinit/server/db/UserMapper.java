package de.hdm.pinit.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.pinit.shared.bo.User;
import de.hdm.pinit.server.db.DBConnection;

public class UserMapper {

	/**
	 * Mapper-Klassen dienen dazu, Objekte auf eine relationale DB abzubilden.
	 * Das Verfahren des Mappings verläuft bidirektional, das heißt, dass die
	 * Objekte in Tupel umgewandelt werden und die Tupel in Objekte. Hier werden
	 * Methoden definiert, die zum Beispiel zur Erstellung, Modifizierung und
	 * Suche von User-Objekten verhelfen.
	 */

	/**
	 * Genauso wie in der DBConnection, muss die Singleton-Eigenschaft gegeben
	 * sein, das heißt, jede Mapper-Klasse wird nur einmal instanziiert. Soll
	 * eine Instanz dieser Klasse erstellt werden, wird immer auf diese
	 * Klassenvariable zurückgegriffen. Sie speichert die einzige Instanz dieser
	 * Klasse.
	 */

	private static UserMapper userMapper = null;

	/**
	 * Die Zugriffsvariable auf den No-Argument Konstruktor muss protected oder
	 * private sein, damit eine Instanziierung von außen nicht zulässig ist. Es
	 * soll verhindert werden, dass über den new-Operator neue Instanzen dieser
	 * Klasse erzeugt werden.
	 */
	protected UserMapper() {

	}

	/**
	 * statische Methode, um eine Instanz von <code>UserMapper</code> zu
	 * erstellen. Wenn es schon eine Instanz dieser Klasse gibt, wird diese
	 * einfach zurückgegeben.
	 * 
	 * @return userMapper
	 */
	public static UserMapper userMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}

		return userMapper;
	}

	/**
	 * Einfügen eines User-Objektes in die DB
	 */

	public User insert(User u) {

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
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM user");

			/*
			 * Next ist wie ein Iterator, setzt immer auf das aktuelle Element
			 * in der Ergebnistabelle und gibt true zurück, wenn ein nächstes
			 * Element existiert und false, wenn keins existiert.
			 */
			if (rs.next()) {
				/*
				 * u erhält den bisher maximalen, nun um 1 inkrementierten
				 * Primärschlüssel.
				 */
				u.setId(rs.getInt("maxid") + 1);

				stmt = con.createStatement();

				/*
				 * Einfügeoperation. Damit die Objekte auf die Tabelle
				 * abgebildet werden. Wir holen uns über unser UserObjekt die
				 * Werte, die auf die Tabelle abgebildet werden sollen.
				 */
				stmt.executeUpdate("INSERT INTO user (id, email, prename, surname, nickname, createdate) " + "VALUES ("
						+ u.getId() + ",'" + u.getEmail() + "','" + u.getPrename() + "','" + u.getSurname() + "','"
						+ u.getNickname() + "','" + u.getCreateDate() + "')");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * Rückgabe des Users, aus stilistischen Gründen.
		 */

		return u;
	}
	
	/**
	 * Überschreiben eines Objekts in die Datenbank.
	 */
	public User update(User u) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE user " + "SET nickname=\"" + u.getNickname() + "\" " + "WHERE id=" + u.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}

	/**
	 * Löschen der Daten eines <code>User</code>-Objekts aus der Datenbank.
	 */
	public void delete(User u) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM user " + "WHERE id=" + u.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Auslesen aller User-Objekte mit gegebenem Nicknamen
	 */
	public User findByNickname(String nickname) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, email, prename, surname, nickname, createdate " 
					+ "FROM user "
					+ "WHERE nickname='" + nickname + "'" );

			/*
			 * Für jeden Eintrag in der Ergebnistabelle wird ein Objekt abgebildet. 
			 * Diese Ergebnisse werden in ein UserObjekt mit den Setter-Methoden rein gepackt. 
			 */
			if (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setEmail(rs.getString("email"));
				u.setPrename(rs.getString("prename"));
				u.setSurname(rs.getString("surname"));
				u.setNickname(rs.getString("nickname"));
				u.setCreateDate(rs.getTimestamp("createdate"));
				
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
		return null;
	}
	
	
	public User findById(int id) {
		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, email, prename, surname, nickname, createdate " 
					+ "FROM user "
					+ "WHERE id=" + id + " ORDER BY nickname");

			/*
			 * Für jeden Eintrag in der Ergebnistabelle wird ein Objekt abgebildet. 
			 * Diese Ergebnisse werden in ein UserObjekt mit den Setter-Methoden rein gepackt. 
			 * Ergebnis Tupel in ein Objekt umwandeln.
			 */
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setEmail(rs.getString("email"));
				u.setPrename(rs.getString("prename"));
				u.setSurname(rs.getString("surname"));
				u.setNickname(rs.getString("nickname"));
				u.setCreateDate(rs.getTimestamp("createdate"));
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}
	
	public Vector<User> findAll(){
		Connection con = DBConnection.connection();
		
		Vector<User> result = new Vector<User>();
		
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * "
					+ " FROM user ");
			
			
			while (rs.next()){
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setPrename(rs.getString("prename"));
				u.setSurname(rs.getString("surname"));
				u.setNickname(rs.getString("nickname"));
				u.setEmail(rs.getString("email"));
				u.setCreateDate(rs.getTimestamp("createdate"));
				
				
				result.add(u);
				} 
			}  
		catch (SQLException e) {
		e.printStackTrace();
		}
		return result;
		
	}
	

}
