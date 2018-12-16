package de.hdm.pinit.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.pinit.shared.PinitServiceAsync;
import de.hdm.pinit.shared.bo.Subscription;
import de.hdm.pinit.shared.bo.User;

public class FindSubscription extends DialogBox {

	MultiWordSuggestOracle selection = new MultiWordSuggestOracle();

	User u = new User();

	PinitServiceAsync pinitService = ClientSideSettings.getPinitService();

	Button subscribeBtn = new Button("Abonnieren");
	Button cancelBtn = new Button("Abbrechen");

	// Label lbNickname = new Label("Nickname: ");
	// TextBox txtNickname = new TextBox();

	SuggestBox aboBox = new SuggestBox(selection);

	public FindSubscription(){		
		  
		VerticalPanel aboPanel = new VerticalPanel();
		this.setText("Wählen Sie Nutzer aus, die Sie abonnieren möchten.");
		this.setAnimationEnabled(false);
		this.setGlassEnabled(true);
		this.center();
		pinitService.getAllUser(new AllUserAsyncCallback());
		
		aboPanel.add(aboBox);
		aboPanel.add(subscribeBtn);
		aboPanel.add(cancelBtn);
		
		
		
		subscribeBtn.addClickHandler(new SubscribeClickhandler());
		
		cancelBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				hide();
				
			}
		});
		
		this.add(aboPanel);

		
		
	}

	public class SubscribeClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			pinitService.checkSubscription(Integer.parseInt(Cookies.getCookie("id")), aboBox.getValue(),
					new CheckAsyncCallback());
		}

	}

	class CheckAsyncCallback implements AsyncCallback<Boolean> {

		@Override
		public void onFailure(Throwable caught) {

		}

		public void onSuccess(Boolean result) {
		
			if (result == true) {
				pinitService.createSubscription(Integer.parseInt(Cookies.getCookie("id")), aboBox.getValue(),
						new AbonnementAsyncCallback());
			} else {
				Window.alert("Dieser Nutzer wurde schon abonniert.");
			}

		}

	}

	class AllUserAsyncCallback implements AsyncCallback<Vector<User>> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Vector<User> result) {
			for (User user : result) {
				selection.add(user.getNickname());
			}

		}

	}

	class AbonnementAsyncCallback implements AsyncCallback<Subscription> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Subscription result) {
			// TODO Auto-generated method stub
			Window.alert("Abonnement wurde angelegt!");
			RootPanel.get("Navigator").clear();
			RootPanel.get("Navigator").add(new PinboardCellList());
			hide();

		}

	}

}
