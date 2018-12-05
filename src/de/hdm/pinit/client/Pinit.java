package de.hdm.pinit.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.pinit.client.gui.ClientSideSettings;
import de.hdm.pinit.client.gui.PinboardCellList;
import de.hdm.pinit.shared.LoginServiceAsync;
import de.hdm.pinit.shared.PinitServiceAsync;
import de.hdm.pinit.shared.bo.Pinboard;
import de.hdm.pinit.shared.bo.User;

/**
 * Entry point classes define <code>onModuleLoad()</code>. Aufgabe der
 * Entrypoint
 */
public class Pinit implements EntryPoint {

	// Deklarieren einer Variable mit dem Referenzdatentyp PinitServiceAsync.
	private PinitServiceAsync pinitService = null;
	private LoginServiceAsync loginService;

	private LoginInfo loginInfo = null;
	private Button logout = new Button("Logout");
	private Button loginButton = new Button("Login");
	private Button subbtn = new Button("Neues Abonnement");
	private Button myPinboardBtn = new Button ("Meine Pinnwand");
	private VerticalPanel subPanel = new VerticalPanel();

	private VerticalPanel loginPanel = new VerticalPanel();
	private Label welcomeLabel = new Label("Bitte logge dich ein, um zu deiner persönlichen Pinnwand zu gelangen!");
	private Label loginLabel = new Label("Herzlich Willkommen bei Pinit!");

	private Anchor signInLink = new Anchor("Login");
	private Anchor signOutLink = new Anchor("Logout");

	Label user = new Label();
	Label nickname = new Label();

	/*
	 * <code>onModuleLoad()</code> muss implementiert werden, da es Bestandteil
	 * des Interfaces EntryPoint ist und wir diese implementieren
	 * 
	 * In der onModuleLoad werden 2 Proxy Objekte generiert. Außerdem wird die
	 * login() Methode aufgerufen, die einen User einloggt bzw. ihn auf das
	 * Registrierungsformular weiterleitet.
	 */
	@Override
	public void onModuleLoad() {
		/*
		 * Prüfung ob die Referenzvariable pinitService bereits mit einem Wert
		 * belegt ist. Ist dies nicht der Fall, so wird die Methode
		 * getPinitService aus der Klasse ClientSideSettings aufgerufen. Das
		 * Ergebnis der Methode wird dann pinitService zugewiesen.
		 */
		if (pinitService == null) {
			pinitService = ClientSideSettings.getPinitService();
		}

		/*
		 * Prüfung ob die Referenzvariable loginService bereits mit einem Wert
		 * belegt ist. Ist dies nicht der Fall, so wird die Methode
		 * getLoginService aus der Klasse ClienStideSettings aufgerufen. Das
		 * Ergebnis der Methode wird dann loginService zugewiesen.
		 */
		loginService = ClientSideSettings.getLoginService();
		/*
		 * die Methode Login aus dem asynchronen Interface LoginService wird
		 * aufgerufen und die HostPageURL wird übergeben???
		 */

		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {

			// was ist das hier für ein Konstrukt?? ()

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler: " + caught.toString());

			}

			/*
			 * 
			 */

			@Override
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if (loginInfo.isLoggedIn()) {

					/*
					 * es wird geprüft, ob derjenige, der sich einloggen möchte,
					 * bereits ein User ist. Wenn er ein User ist, der sich
					 * bereits bei Pinit registriert hat, werden Cookies gesetzt
					 * und die Methode loadPinitAdmin aufgerufen.
					 */
					pinitService.checkUser(loginInfo.getEmailAddress(), new AsyncCallback<User>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Login fehlgeschlagen!");
						}

						@Override
						public void onSuccess(User result) {

							if (result != null) {

								/*
								 * Cookie wird gesetzt, um später einfacher auf
								 * den eingeloggten User zugreifen zu können.
								 */
								Cookies.setCookie("id", "" + result.getId());
								Cookies.setCookie("email", result.getEmail());
								loadPinitAdmin(result.getId());
							}

							/*
							 * falls der User noch nicht registriert ist, wird
							 * er zum Registrierungsformular weitergeleitet.
							 */
							else {

								Window.alert("Sie sind noch nicht bei Pinit registriert");
								RootPanel.get("details").clear();

								// RegistrationForm wird aufgerufen, damit der
								// User sich registrieren kann
								RootPanel.get("details").add(new RegistrationForm());
							}
						}
					});

				} else {
					loadLogin();
				}

			}
		});

	}

	/*
	 * Methode um Login-Ansicht zu laden.
	 */

	public void loadLogin() {

		loginPanel.setSpacing(20);
		loginPanel.add(welcomeLabel);
		loginPanel.add(loginLabel);
		

		signInLink.setHref(loginInfo.getLoginUrl());

		/*
		 * das LoginPanel und der loginButton werden den div's Details bzw.
		 * Navigator zugeordnet, welche auf dem RootPanel liegen
		 */
		RootPanel.get("details").add(loginPanel);
		RootPanel.get("details").clear();
		RootPanel.get("details").add(loginButton);
		
		loginButton.setWidth("100px");
		loginButton.setStylePrimaryName("login-button");
		loginButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.open(signInLink.getHref(), "_self", "");
			}
		});
	}

	private void loadPinitAdmin(int id) {
		// Erstellen des Logout-Links
		signOutLink.setHref(loginInfo.getLogoutUrl());
		PinitServiceAsync pinitService = ClientSideSettings.getPinitService();

		pinitService.getUserById(id, new AsyncCallback<User>() {

			@Override
			public void onSuccess(User result) {
				nickname.setText("Nickname: " + result.getNickname());
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler");
			}
		});
		RootPanel.get("details").clear();
		
		RootPanel.get("header").add(logout);
		
		RootPanel.get("subscription").clear();
		RootPanel.get("subscription").add(subPanel);
		
		RootPanel.get("pinboardlist").add(new PinboardCellList());
		
		
		subPanel.add(subbtn);
		subPanel.add(myPinboardBtn);
		subbtn.setWidth("160px");
		myPinboardBtn.setWidth("160px");
		subbtn.setStylePrimaryName("subscription-button");
		myPinboardBtn.setStylePrimaryName("mypinboard-button");
		logout.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		logout.setWidth("100px");
		logout.setStylePrimaryName("logout-button");

		logout.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.open(signOutLink.getHref(), "_self", "");
			}
		});

	}

	public class RegistrationForm extends VerticalPanel {

		PinitServiceAsync pinitService = ClientSideSettings.getPinitService();
		User u = null;
		VerticalPanel registerPanel = new VerticalPanel();

		Label nickname = new Label("Nickname: ");
		Label email = new Label("E-Mail: ");
		TextBox nicknameBox = new TextBox();
		TextBox emailBox = new TextBox();

		Button registerButton = new Button("Registrieren");

		public void onLoad() {
			super.onLoad();

			Grid registerGrid = new Grid(3,2);
			registerPanel.add(registerGrid);

			registerGrid.setWidget(0, 0, nickname);
			registerGrid.setWidget(0, 1, nicknameBox);

			registerGrid.setWidget(1, 0, email);
			registerGrid.setWidget(1, 1, emailBox);

			registerGrid.setWidget(2, 0, registerButton);
			registerButton.addClickHandler(new AnmeldenClickhandler());
			this.add(registerPanel);
		}

		private class AnmeldenClickhandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				String nickname = nicknameBox.getText();
				String email = emailBox.getText();

				pinitService.createUser(nickname, email, new CreateUserCallback());

			}

		}

		class CreateUserCallback implements AsyncCallback<User> {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Registrieren ist Fehlgeschlagen! " + caught.getMessage());
			}

			@Override
			public void onSuccess(User user) {
				Cookies.setCookie("id", ""+user.getId());
				Cookies.setCookie("email", user.getEmail());

				pinitService.createPinboard(user.getId(), new CreatePinboardCallback());

				Window.alert("Glückwunsch " + nicknameBox.getText()+ " ! Sie sind jetzt Mitglied bei Pinit!");

				loadPinitAdmin(user.getId());

			}

			class CreatePinboardCallback implements AsyncCallback<Pinboard> {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Das Registrieren ist Fehlgeschlagen!" + caught.getMessage());

				}

				@Override
				public void onSuccess(Pinboard result) {
				}
			}

		}

	}

}
