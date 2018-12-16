package de.hdm.pinit.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.pinit.shared.bo.Pinboard;
import de.hdm.pinit.shared.bo.Posting;
import de.hdm.pinit.shared.bo.Subscription;
import de.hdm.pinit.shared.bo.User;

/**
 * Asyncrones Gegenstück zu <code>PinitService</code>.
 */
public interface PinitServiceAsync {

	void createUser(String nickname, String email, AsyncCallback<User> callback);

	void getUserById(int id, AsyncCallback<User> callback);

	void init(AsyncCallback<Void> callback);

	void createPinboard(int ownerid, AsyncCallback<Pinboard> callback);

	void getPinboardByOwner(int userId, AsyncCallback<Pinboard> callback);

	void createSubscription(int userId, int pinboardId, AsyncCallback<Subscription> callback);

	void getSubscriptionByUser(int userId, AsyncCallback<Vector<Subscription>> callback);

	void getAllUser(AsyncCallback<Vector<User>> callback);

	void checkUser(String email, AsyncCallback<User> callback);
	
	void getOwnerByPinboard(Pinboard p, AsyncCallback<User> callback);

	void getPinboardBySubscription(Subscription s, AsyncCallback<Pinboard> callback);

	void getAllSubscriptionsByUser(int userId, AsyncCallback<Vector<User>> callback);

	void deleteSubscription(int userId, String nickname, AsyncCallback<Void> callback);

	void getUserByNickname(String nickname, AsyncCallback<User> callback);

	void getPostingsByPinboard(int id, AsyncCallback<Vector<Posting>> callback);

	void getPostingsByPinboardOwner(int ownerId, AsyncCallback<Vector<Posting>> callback);

	void deletePosting(Posting p, AsyncCallback<Void> callback);

	void createPosting(int ownerId, String text, AsyncCallback<Posting> callback);
	

}
