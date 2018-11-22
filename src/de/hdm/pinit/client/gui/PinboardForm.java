package de.hdm.pinit.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.pinit.shared.PinitServiceAsync;
import de.hdm.pinit.shared.bo.Pinboard;

public class PinboardForm extends VerticalPanel {

	PinitServiceAsync pinitService = ClientSideSettings.getPinitService();
		

	Pinboard pinboardToDisplay = null;
	
	Label nicknameLabel = new Label("Meine Pinnwand");
	Button removeBtn = new Button("Abonnement aufheben");

	public void onLoad() {
		super.onLoad();
		this.add(nicknameLabel);
		this.add(removeBtn);
	}
	
	void setSelected(Pinboard p) {
		
		if(p != null) {
			pinboardToDisplay = p;
			removeBtn.setEnabled(true);
			nicknameLabel.setText(pinboardToDisplay.getUser().getNickname());			
		}else {
			removeBtn.setEnabled(false);
			nicknameLabel.setText("");
		}
	}

}


