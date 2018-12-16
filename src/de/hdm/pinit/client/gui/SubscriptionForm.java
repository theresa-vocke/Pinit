package de.hdm.pinit.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.pinit.shared.PinitServiceAsync;
import de.hdm.pinit.shared.bo.User;

public class SubscriptionForm extends VerticalPanel {

	
	//Label test = new Label();
	String nickname = null;
	//HorizontalPanel eins = new HorizontalPanel();
	PinitServiceAsync pinitService = ClientSideSettings.getPinitService();
	Button removeBtn = new Button("Nutzer deabonnieren");
	
	public SubscriptionForm(int userId) {
		// TODO Auto-generated constructor stub
		pinitService.getUserById(userId, new LoadPinboardCallback());
		
		removeBtn.addClickHandler(new RemoveClickHandler());
		removeBtn.setStylePrimaryName("remove-button");
		//eins.add(test);
		//this.add(removeBtn);
		RootPanel.get("details").clear();
		RootPanel.get("details").add(removeBtn);
		//this.add(eins);
	}
	
	public class LoadPinboardCallback implements AsyncCallback<User> {

		@Override
		public void onSuccess(User result) {
			// TODO Auto-generated method stub
			Window.alert("Jetzt siehst du die Pinnwand");
			//hier noch rein, dass auch die Beiträge von dem jeweiligen User geladen werden
			
		}
		
		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("Fehler");
		}
		
	}
	
	public class RemoveClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			pinitService.deleteSubscription(Integer.parseInt(Cookies.getCookie("id")), nickname, new DeaboniereAsyncCallback());
			
		}
	}
	
	public class DeaboniereAsyncCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("User wurde nicht deabonniert!");
		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Window.alert("User wurde deabonniert!");
			RootPanel.get("details").clear();
			RootPanel.get("navigator").clear();
			RootPanel.get("navigator").add(new PinboardCellList());
			
		}
		
	}

}
