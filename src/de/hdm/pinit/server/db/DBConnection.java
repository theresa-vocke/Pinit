package de.hdm.pinit.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

/**
 * In dieser Klasse findet die Verwaltung zum Verbindungsaufbau der Datenbank
 * statt. Durch die Singleton-Eigenschaft, die hier eingebunden wird, greifen
 * wir immer auf eine klar vordefinierte Datenbank zu. Dadurch wird
 * gewährleistet dass immer nur eine Instanz existieren kann und es somit nur
 * eine einzige Verbindung zur Datenbank gibt. Die Verbindung wird hierüber
 * überprüft und bei vorhandener Verbindung entweder über das return
 * zurückgegeben oder eben neu instanziiert bzw. erstellt.
 * 
 * @author Miescha, Theresa
 *
 */

public class DBConnection {

	/*
	 * Das Interface Connection stellt uns eine Sitzung mit einer speziellen DB zur
	 * Verfügung. Anweisungen werden entsprechend ausgeführt und dementsprechende
	 * Ergebnisse werden im Kontext einer Verbindung über unsere Referenzvariable
	 * con zurückgegeben. Durch die Kennzeichnung static, kann nur eine einzige
	 * Instanz einer Klasse erstellt werden.
	 */

	// In der Variable con vom Typ Connection wird die einzige Instanz dieser Klasse
	// gespeichert.
	private static Connection con = null;

	/*
	 * Mit der jeweiligen URL wird die bestimmte DB angesprochen. Bei
	 * professionellen Anwendungen wird die URL nicht direkt miteingebunden, sondern
	 * bspw. über eine Konfigurationsdatei eingelesen.
	 */

	// URL für die Verbindung zur lokalen DB
	private static String localUrl = "jdbc:mysql://localhost:3306/pinit?user=pinit&password=pinitpw";

	// URL für die Verbindung zur GoogleDB
	private static String googleUrl = "";

	/*
	 * Bei einer Verbindung zur DB, muss eine Instanz von Connection erzeugt werden.
	 * Da wir jedoch die Singleton-Eigenschaft gewährleisten möchten, kann dies nur
	 * über die statische Methode erfolgen. Innerhalb der Mapperklassen wird also
	 * mittels DBconnection.connection() die Verbindung zur DB hergestellt und kann
	 * nicht über den new-Operator erstellt werden.
	 */
	public static Connection connection() {

		// Wenn bislang keine DBVerbindung hergestellt ist
		if (con == null) {

			// die lokale variable url wird angelegt und hat noch keinen Wert
			String url = null;

			try {
				/*
				 * Zustand des Programms wird überprüft bzgl. GoogleDeployment. Die Überprüfung
				 * des Production-Status gibt Informationen darüber, ob das Programm schon
				 * deployed wurde. Falls es noch nicht deployed wurde, wird ein anderer
				 * DBTreiber benötigt.
				 */
				if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {

					/*
					 * Die Klasse die so heißt wie das Argument, welches als String übergeben wird,
					 * wird zur Laufzeit zugreifbar. Über den String wird gesagt welche Klasse zu
					 * instanziieren ist. Nämlich ein Objekt des entsprechden Treibers.
					 */
					Class.forName("com.mysql.jdbc.GoogleDriver");
					url = googleUrl;
				} else {

					// Lokale MySQLinstanz die während der Entwicklung verwendet wird.
					Class.forName("com.mysql.jdbc.Driver");
					url = localUrl;
				}

				/*
				 * Dann erst kann uns der DriverManager eine Verbindung mit den oben in der
				 * Variable url angegebenen Verbindungsinformationen aufbauen. Diese Verbindung
				 * wird dann in der statischen Variable con abgespeichert und fortan verwendet.
				 */

				// die oben ausgewählte Klasse(Driver), wird hier an der Stelle instantiiert
				con = DriverManager.getConnection(url);

				/*
				 * Wenn eine Exception geworfen wird, wird sie hier aufgefangen. Wenn das
				 * passiert wird die statische Variable con null gesetzt und gibt Informationen
				 * und verhilft bei der Ermittlung der Ausnahme.
				 */
			} catch (Exception e) {
				con = null;
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}

		// Die Referenz auf Connection wird zurückgegeben. 
		return con;
	}

}