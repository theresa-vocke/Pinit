package de.hdm.pinit.shared;

import java.sql.Timestamp;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.pinit.shared.bo.Pinboard;
import de.hdm.pinit.shared.bo.User;

/**
 * Asyncrones Gegenstück zu <code>PinitService</code>.
 */
public interface PinitServiceAsync {

	void createUser(int id, String nickname, String email, Timestamp createDate, AsyncCallback<User> callback);

	void getUserById(int id, AsyncCallback<User> callback);

	void init(AsyncCallback<Void> callback);

	void createPinboard(int id, int ownerId, Timestamp createDate, AsyncCallback<Pinboard> callback);

	void getPinboardByOwner(int ownerId, AsyncCallback<Pinboard> callback);
	
	
}
