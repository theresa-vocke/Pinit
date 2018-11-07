//package test;
//
//import static org.junit.Assert.assertTrue;
//import de.hdm.pinit.server.PinitServiceImpl;
//import org.junit.jupiter.api.Test;
//
//import com.google.gwt.junit.client.GWTTestCase;
//
//public class PinitServiceImplTest extends GWTTestCase {
//
//		/**
//		 * Benötigte Methode diese implementiert werden muss, um auf den korrekten Pfad der Klasse zugreifen zu können.  
//		 * Hier wird der Modulpfad der gwt.xml-Datei eingetragen.
//		 */
//		public String getModuleName() {
//			return "de.hdm.pinit.server.PinitServiceImpl";
//		}
//		
//		/**
//		 * Dient zur Überprüfung ob die Methoden und Testdurchführung funktionieren.
//		 */
//
//		public void testSimple() {
//			assertTrue(true);
//		}
//		
//		/*
//		   * ***************************************************************************
//		   * ABSCHNITT, Anfang: Methoden fuer Nutzer-Objekte Test
//		   * ***************************************************************************
//		   */
//		
//		/**
//		 * Test Case 
//		 */
//		
//		public void createUser() {
//			
//			
//			String email = "tm@gmail.com";
//			String nickname = "tm";
//					
//			//Verbindung zur Test Klasse herstellen 
//			PinitServiceImpl pinit = new PinitServiceImpl();
//			
//			assertNotNull(pinit);
//			
//			
//			pinit.createUser(email, nickname);
//		}
//		
//	
//		
//		
//}
