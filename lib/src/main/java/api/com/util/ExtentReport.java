package api.com.util;

import java.io.File;
import java.io.IOException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReport {
	
	public static ExtentReports extentReport = null;
	public static ExtentTest extentlog;
	
	public static void initialize(String path) throws IOException {
		if(extentReport == null) {
		extentReport = new ExtentReports(path, true);
		//extentReport.addSystemInfo("userName", System.getProperty("user.name"));
		extentReport.addSystemInfo("Environment", Helper.configReader("env"));
		extentReport.addSystemInfo("Build", Helper.configReader("Build_Version"));
		extentReport.loadConfig(new File(System.getProperty("user.dir")+"/extent-report.xml"));
		
		}
		
	}

}
