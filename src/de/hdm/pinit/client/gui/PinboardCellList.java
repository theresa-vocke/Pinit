package de.hdm.pinit.client.gui;

import java.util.Vector;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import de.hdm.pinit.shared.PinitServiceAsync;
import de.hdm.pinit.shared.bo.User;

public class PinboardCellList extends VerticalPanel {

	// Leeres Panel, worin später die Celllist platziert wird.
	VerticalPanel cellListPanel = new VerticalPanel();

	// Neue Zelle, die später in die Celllist geladen wird.
	PinboardCell cell = new PinboardCell();
	
	// Leeres Abonnement-Formular, dieses später bei Auswahl einer Aktion geöffnet wird
	SubscriptionForm subscriptionForm = new SubscriptionForm();

	// Zugriff auf unsere Async-Methoden über unser Proxy
	PinitServiceAsync pinitService = ClientSideSettings.getPinitService();

	/*
	 * Definieren eines Schlüssels für ein Nutzer-Objekt, damit dieser bei
	 * Veränderung beibehalten bzw. identifiziert werden kann
	 * 
	 */
	ProvidesKey<User> userKeyProvider = new ProvidesKey<User>() {

		/*
		 * Die Methode getKey ist Inhalt der anonymen Klasse ProvidesKey Die Methode
		 * kann deshalb nur an dieser Stelle verwendet werden
		 */
		public Object getKey(User u) {

			// Prüfung ob ein User-Objekt vorhanden ist
			return (u == null) ? null : u.getId();
		}
		// Semikolon da Syntax für die Anweisung der anonyme Klasse zu schließen
	};

	/*
	 * Um die Handlung des Benutzers zu steuern, benötigen wir das Interface
	 * SingleSelectionModel Damit es für immer festgelegt ist wird es als Final
	 * deklariert
	 */
	final SingleSelectionModel<User> selectionModel = new SingleSelectionModel<User>();

	// CellList anlegen und die jeweiligen Zellen mit dem zugehörigen Schlüssel
	// übergeben
	CellList<User> pinboardCellList = new CellList<User>(cell, userKeyProvider);

	/**
	 * Damit die Widgets geladen werden können, muss die onLoad()-Methode
	 * implementiert werden. Darin wird definiert, welcher Vorgang bei der
	 * Aktivierung der Widgets ausgeführt werden soll. Die Methode wird direkt nach
	 * dem Anhängen eines Widgets an das Dokument des Browser aufgerufen.
	 * 
	 */
	public void onLoad() {

		/*
		 * Damit alle weiteren Elemente der Superklasse geladen werden und somit die
		 * Sinnhaftigkeit und die Semantik bereits aktiviert ist
		 */
		super.onLoad();

		// Dem VerticalPanel wird unser Celllist hinzugefügt
		cellListPanel.add(pinboardCellList);

		// Diesem VerticalPanel wird nun das tatsächliche Panel hinzugefügt.
		this.add(cellListPanel);

		// Unsere CellList wird nun unserem SelectionModel hinzugefügt
		pinboardCellList.setSelectionModel(selectionModel);

		/*
		 * Über unser Proxy wird die Methode aufgerufen die einen User übergibt und
		 * darüber Informationen über die abonnierten Pinnwände des eingeloggten Nutzers
		 * zurückgeben soll
		 */
		pinitService.getAllSubscriptionsByUser(Integer.parseInt(Cookies.getCookie("id")),
				new LoadCellListDataCallback());

		/*
		 * Damit unsere Liste Aktionen annehmen und ausführen kann, benötigen wir das Interface 
		 * SelectionChangeEvent.Handler, welcher vom SingleSelectionModel zur Verfügung
		 * gestellt wird. Un
		 */
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			/*
			 *Bei Auswahl eines Elements bzw. Zelle innerhalb der CellList, wird nun die 
			 *folgende Aktion ausgelöst. Dies wird in einer anonymen Klasse definiert, worin 
			 *nun die Methode deklariert wird. 
			 */
			
			public void onSelectionChange(SelectionChangeEvent event) {
				
				/*Der ausgewählte User wird über das selectionModel geholt und der Variable 
				*selectedUser zugewiesen.
				*/
				User selectedUser = selectionModel.getSelectedObject();
				
			if (selectedUser != null) {
								
				RootPanel.get("details").clear();
				RootPanel.get("details").add(subscriptionForm);
			}
			
				
			}
		});

		
	}

	/*
	 * Das CallbackObjekt innerhalb der Methode, löst folgende innere Klasse aus.
	 * Zudem muss das Interface AsynCallBack implementiert werden, worin die
	 * Anweisung der Ausführung des CallbackObjekts hinterlegt ist. Hierzu gibt es
	 * zwei Fälle die bei Rückgabe eintreffen können.
	 */
	public class LoadCellListDataCallback implements AsyncCallback<Vector<User>> {

		// Bei einem Fehlschlag, tritt die Methode ein, die Informationen darüber gibt
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Ihre Abonnements konnten nicht geladen werden!");
		}

		/*
		 * Wird das CallbackObjekt ordnungsgemäß zurückgegeben, so wird die
		 * Methode(non-Javadoc) aufgerufen, die die folgenden Aktionen ausführt.
		 */
		@Override
		public void onSuccess(Vector<User> result) {

			/*
			 * Zur erhaltenen cellList wird über setRowdata ein StartIndex festgelegt, sowie
			 * die Werte bzw. Inhalte der Zellen die geladen werden sollen
			 */
			pinboardCellList.setRowData(0, result);

			// Die Gesamtanzahl der Reihen wird exakt gezählt
			pinboardCellList.setRowCount(result.size(), true);

			/*
			 * Damit die Gesamtanzahl der Reihen immer korrekt angezeigt wird, werden die
			 * Daten hierüber neugeladen
			 */
			pinboardCellList.redraw();
		}

	}

}