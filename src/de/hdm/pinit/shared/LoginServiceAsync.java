package de.hdm.pinit.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.pinit.client.LoginInfo;

public interface LoginServiceAsync {

	void login(String requestUri, AsyncCallback<LoginInfo> callback);

}
