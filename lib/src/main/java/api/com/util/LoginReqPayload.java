package api.com.util;

import java.io.File;

public class LoginReqPayload {
	
	
	public static final String loginPayload = System.getProperty("user.dir")+"/Resources/TestData/LoginReq.json";
	
	public static File readLoginReq() {
		
		
		File file = new File(loginPayload);
		return file;
	}

}
