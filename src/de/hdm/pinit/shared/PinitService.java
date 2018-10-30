package de.hdm.pinit.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("pinitservice")
public interface PinitService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
}
