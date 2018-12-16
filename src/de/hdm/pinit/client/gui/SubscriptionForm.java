package de.hdm.pinit.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.Vector;

import de.hdm.pinit.shared.PinitServiceAsync;
import de.hdm.pinit.shared.bo.Posting;
import de.hdm.pinit.shared.bo.User;

public class SubscriptionForm extends VerticalPanel {

	String nickname = null;
	int currentUser = 0;
	PinitServiceAsync pinitService = ClientSideSettings.getPinitService();
	Button removeBtn = new Button("Nutzer deabonnieren");

	VerticalPanel postingContent = new VerticalPanel();
	
	final FlexTable postingTable = new FlexTable();

	ScrollPanel postingScroll = new ScrollPanel();
	
	VerticalPanel textPanel = null;
	
	Label textContent = null;
	Label createdate = null;

	public SubscriptionForm(final int userId) {

		this.currentUser = userId;
		postingScroll.setSize("900px", "550px");
		pinitService.getUserById(userId, new LoadPinboardCallback());

		removeBtn.addClickHandler(new RemoveClickHandler());
		removeBtn.setStylePrimaryName("remove-button");
				
		postingScroll.add(postingTable);
		
		this.add(postingScroll);
		this.add(removeBtn);

	}

	public class LoadPinboardCallback implements AsyncCallback<User> {

		@Override
		public void onSuccess(User result) {
			pinitService.getPostingsByPinboardOwner(result.getId(), new PostingCallback());
			nickname = result.getNickname();
		}

		@Override
		public void onFailure(Throwable caught) {

			Window.alert("Fehler");
		}

	}

	public class PostingCallback implements AsyncCallback<Vector<Posting>> {

		@Override
		public void onFailure(Throwable arg0) {
			Window.alert("Das war wohl nix.");

		}

		@Override
		public void onSuccess(Vector<Posting> result) {
			if (result.size() != 0) {
				for (Posting posting : result) {

					textPanel = new VerticalPanel();
					
					int rows = postingTable.getRowCount();
					
					textContent = new Label();
					createdate = new Label();

					createdate.setText(posting.getCreatedate().toString());

					final Grid postingGrid = new Grid(2, 2);
					textContent.setStylePrimaryName("posting-content");
					textContent.setText(posting.getText());

					postingGrid.setWidget(0, 0, textContent);
					postingGrid.setWidget(1, 0, createdate);

					postingGrid.setStylePrimaryName("posting-grid");
					
					
					postingTable.setWidget(rows, 0, textPanel);
					
					textPanel.add(postingGrid);

				}
			} else {
				Window.alert("Momentan noch keine Beiträge vorhanden!");
			}
		}

	}

	public class RemoveClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			pinitService.deleteSubscription(Integer.parseInt(Cookies.getCookie("id")), nickname,
					new RemoveAsyncCallback());

		}
	}

	public class RemoveAsyncCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("User wurde nicht deabonniert!");
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("User wurde deabonniert!");
			RootPanel.get("details").clear();
			RootPanel.get("navigator").clear();
			RootPanel.get("navigator").add(new PinboardCellList());

		}

	}

}
