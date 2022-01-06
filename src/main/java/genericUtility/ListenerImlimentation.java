package genericUtility;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;

public class ListenerImlimentation implements ITestListener{
	
	ExtentTest test;
	public  void onTestStart(ITestResult result) {
	    // not implemented
		test = reports.createTest(result.getMethod().getMethodName());
		
	  }

	 
	public void onTestSuccess(ITestResult result) {
	    // not implemented
		test.log(Status.PASS, result.getMethod().getMethodName()+ "is passed");
	  }

	public void onTestFailure(ITestResult result) {
	    // not implemented
		WebDriver driver;
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
			EventFiringWebDriver efd = new EventFiringWebDriver(driver);
			File sourceFile = efd.getScreenshotAs(OutputType.FILE);
			File destinationFile = new File("./screenshots/"+result.getMethod().getMethodName()+"_.png");
			Files.copy(sourceFile, destinationFile);
			test.addScreenCaptureFromPath(destinationFile.getAbsolutePath());

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test.log(Status.FAIL, result.getMethod().getMethodName()+" is failed");
		test.log(Status.FAIL, result.getThrowable());
	  }

	  /**
	   * Invoked each time a test is skipped.
	   *
	   * @param result <code>ITestResult</code> containing information about the run test
	   * @see ITestResult#SKIP
	   */
	public void onTestSkipped(ITestResult result) {
	    // not implemented
		test.log(Status.SKIP, result.getMethod().getMethodName()+ "is skipped");
		test.log(Status.SKIP, result.getThrowable());
	  }

	  /**
	   * Invoked each time a method fails but has been annotated with successPercentage and this failure
	   * still keeps it within the success percentage requested.
	   *
	   * @param result <code>ITestResult</code> containing information about the run test
	   * @see ITestResult#SUCCESS_PERCENTAGE_FAILURE
	   */
	

	ExtentSparkReporter spark;
	ExtentReports reports;
	  public void onStart(ITestContext context) {
	    // not implemented
//		  configurations and attaching the report
		spark = new ExtentSparkReporter("./extentReports/report.html");
		  spark.config().setDocumentTitle("Regression Test");
		  spark.config().setReportName("MY first extrent report");
		  spark.config().setTheme(Theme.STANDARD);
		  
		  //atach the report
		  reports = new ExtentReports();
		  reports.attachReporter(spark);
		  reports.setSystemInfo("os", "windows 10");
		  reports.setSystemInfo("auther", "pradeep");
		  reports.setSystemInfo("browser", "chrome");
		  
		  

		  
	  }

	  
	  public void onFinish(ITestContext context) {
	    // not implemented
		 reports.flush();
	  }


}
