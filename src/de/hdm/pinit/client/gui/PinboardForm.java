package de.hdm.pinit.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.pinit.shared.PinitServiceAsync;
import de.hdm.pinit.shared.bo.Pinboard;
import de.hdm.pinit.shared.bo.User;

public class PinboardForm extends VerticalPanel {

	PinitServiceAsync pinitService = ClientSideSettings.getPinitService();

	HorizontalPanel btnpanel = null;
	VerticalPanel content = new VerticalPanel();
	VerticalPanel beitragPanel = null;
	final FlexTable flexx = new FlexTable();

	Button beitragBtn = new Button("Pin It !");
	Label user = new Label();
	TextArea text = new TextArea();

	int cookie = 0;
	int pinnwandId = 0;

	Label testlabel = null;
	Label datum = null;

	public PinboardForm(int userId) {
		this.cookie = userId;
		text.setVisibleLines(2);

		pinitService.getUserById(userId, new UserAsyncCallback());
		pinitService.getPinboardByOwner(userId, new PinboardCallBack());
		// pinitService.getPostingByPinboard(ownerId, new postingbeitragCallback());

		content.add(user);
		content.add(text);
		content.add(beitragBtn);
		content.add(flexx);
		this.add(content);
	}

	public class UserAsyncCallback implements AsyncCallback<User> {

		@Override
		public void onSuccess(User result) {
			// TODO Auto-generated method stub
			user.setText("Meine Pinnwand ist die Beste");

		}

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}
	}

	public class PinboardCallBack implements AsyncCallback<Pinboard> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Fehler beim Laden " + caught.getMessage());
		}

		@Override
		public void onSuccess(Pinboard result) {

			beitragBtn.addClickHandler(new NewClickhandler());

		}
	}

	public class NewClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

		}
	}
}
