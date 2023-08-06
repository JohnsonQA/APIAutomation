package api.com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class Helper {
	
	public static final String path = System.getProperty("user.dir")+ "/config.properties";
	public static String configReader(String key) throws IOException {
		
		FileInputStream fis = new FileInputStream(path);
		Properties prop = new Properties();
		prop.load(fis);
		return prop.getProperty(key);	
	}
	
	
	
	public static void createFolder(String path) {
		
		File file = new File(path);//fetch the path and store it in file
		if(!file.exists()) {
		file.mkdir();  //It will create a new folder inside project directory
		}
		else {
			System.out.println("Folder already created");
		}
	}
	
	public static String timeStamp() {
		
		Date now = new Date();
		String time = now.toString().replace(":", "-");
		return time;
	}
}
