package test;

import de.hdm.pinit.server.PinitServiceImpl;
import de.hdm.pinit.server.db.UserMapper;

public class Test {

	public static void main(String[] args) {
		
PinitServiceImpl impl = new PinitServiceImpl();
impl.init();

impl.createUser("resi", "resi@gmail.com");	}

}
