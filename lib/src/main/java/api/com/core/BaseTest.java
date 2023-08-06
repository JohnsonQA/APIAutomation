package api.com.core;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import api.com.util.ExtentReport;
import api.com.util.Helper;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {
	
	@BeforeSuite(alwaysRun = true)
	public void configReport() throws IOException {
		
		
		String folderPath = System.getProperty("user.dir")+"/htmlReports/"+ Helper.timeStamp();
		Helper.createFolder(folderPath);
		ExtentReport.initialize(folderPath + "/"+ "Reports.html");
	}
	
	@BeforeMethod(alwaysRun = true)
	public void testStartUp() {
		
		//final Logging log = Logging
		System.out.println("*************Test Execution Stardted************");
		
	}
	
	@AfterMethod(alwaysRun = true)
	public void getResult(ITestResult result) {
		
		if(result.getStatus()== ITestResult.SUCCESS) {
			
			ExtentReport.extentlog.log(LogStatus.PASS, "Test Case:" +result.getName()+ "is succesfull");
			
		}else if(result.getStatus() == ITestResult.FAILURE) {
			ExtentReport.extentlog.log(LogStatus.FAIL, "Test Case:" +result.getName() + "is failed");
			ExtentReport.extentlog.log(LogStatus.FAIL, "Test Case is failed due to" +result.getThrowable());
			
		}else if (result.getStatus() == ITestResult.SKIP) {
			ExtentReport.extentlog.log(LogStatus.SKIP, "Test case:" +result.getName()+ "is skipped");
		}
		
		ExtentReport.extentReport.endTest(ExtentReport.extentlog);
		
	}
	
	@AfterSuite(alwaysRun = true)
	public void endReport() {
		
		ExtentReport.extentReport.close();
	}
	
	

}
