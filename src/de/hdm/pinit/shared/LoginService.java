package de.hdm.pinit.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.pinit.client.LoginInfo;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {

	public LoginInfo login(String requestUri);
	
}
