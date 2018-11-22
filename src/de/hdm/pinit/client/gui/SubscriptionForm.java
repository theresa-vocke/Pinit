package de.hdm.pinit.client.gui;

import java.util.Vector;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.pinit.shared.PinitServiceAsync;
import de.hdm.pinit.shared.bo.Pinboard;
import de.hdm.pinit.shared.bo.User;

public class SubscriptionForm extends VerticalPanel {

	PinitServiceAsync pinitService = ClientSideSettings.getPinitService();

	Pinboard pinboardToDisplay = null;

	Label nicknameLabel = new Label("Pinnwand von:");
	Button removeBtn = new Button("Abonnement aufheben");

	public void onLoad() {
		super.onLoad();
		this.add(nicknameLabel);
		this.add(removeBtn);
	}

	void setSelected(User u) {
		
			if(u != null) {
			
			
			removeBtn.setEnabled(true);
			nicknameLabel.setText(pinboardToDisplay.getUser().getNickname());	
			
		}else {
			removeBtn.setEnabled(false);
			nicknameLabel.setText("");
		}
	}

	public class SubPinboardCallback implements AsyncCallback<Pinboard> {

		// Bei einem Fehlschlag, tritt die Methode ein, die Informationen darüber gibt
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Die Pinnwand konnte nicht geladen werden!");

		}

		/*
		 * Wird das CallbackObjekt ordnungsgemäß zurückgegeben, so wird die
		 * Methode(non-Javadoc) aufgerufen, die die folgenden Aktionen ausführt.
		 */
		@Override
		public void onSuccess(Pinboard result) {

			// Test
			Window.alert("Die Pinnwand konnte erfolgreich geladen werden!");
		}
	}
}
