package de.hdm.pinit.shared;

import java.sql.Timestamp;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.pinit.shared.bo.Pinboard;
import de.hdm.pinit.shared.bo.Subscription;
import de.hdm.pinit.shared.bo.User;

/**
 * Asyncrones Gegenstück zu <code>PinitService</code>.
 */
public interface PinitServiceAsync {

	void createUser(String nickname, String email, AsyncCallback<User> callback);

	void getUserById(int id, AsyncCallback<User> callback);

	void init(AsyncCallback<Void> callback);

	void createPinboard(User u, AsyncCallback<Pinboard> callback);

	void getPinboardByOwner(User u, AsyncCallback<Pinboard> callback);

	void createSubscription(int userId, int pinboardId, AsyncCallback<Subscription> callback);

	void getSubscriptionByUser(int userId, AsyncCallback<Subscription> callback);
	
	
}
