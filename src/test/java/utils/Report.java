package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.ScreenCapture;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static utils.DriverUtils.takeScreenshot;


public class Report {

	public static ExtentReports ExtentReporterObj;
	public static ExtentTest Reporter;
	public static ExtentSparkReporter SparkReporter;
	public static ScreenCapture mediaEntityModelProviderObj;

	public static void initializeExtentReporter() {
		try {
			ExtentReporterObj = new ExtentReports();
			SparkReporter = new ExtentSparkReporter("./target/spark/index.html");
			ExtentReporterObj.setSystemInfo("Environment", "Amazon Production");
			ExtentReporterObj.setSystemInfo("User Name", System.getProperty("user.name"));
			ExtentReporterObj.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
			ExtentReporterObj.setSystemInfo("Operating System",
					System.getProperty("os.name") + "(" + System.getProperty("os.version") + ")");

			final File CONF = new File(System.getProperty("user.dir")+"/src/test/resources/spark-config.json");
			SparkReporter.loadJSONConfig(CONF);
			ExtentReporterObj.attachReporter(SparkReporter);
		} catch (UnknownHostException e) {
			e.printStackTrace(System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void CreateExtentReport(String scenarioType , String ScenarioName,String ScenarioDescription)  {
		// Start the test using the ExtentTest class object.
		Reporter = ExtentReporterObj.createTest(ScenarioName+ "<br>" + ScenarioDescription);
		Reporter.assignCategory(scenarioType);
//		Reporter.assignAuthor(System.getProperty("user.name"));
//		Reporter.assignDevice("System");
	}

	public static void flushExtentReports() {
		ExtentReporterObj.flush();
	}


	
	public static void logger(LogStatus logType, String Message) {

		switch (logType) {
			case INFO: Reporter.info(Message );break;
			case PASS: Reporter.pass(Message );break;
			case FAIL: Reporter.fail(Message );break;
			case ERROR: Reporter.warning(Message);break;
			case SKIP: Reporter.skip(Message );break;
				case PASS_SCREENSHOT: Reporter.pass("<b>" + Message + "</b>", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());break;
				case INFO_SCREENSHOT: Reporter.info("<b>" + Message + "</b>",MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());break;
				case FAIL_SCREENSHOT: Reporter.fail("<b>" + Message + "</b>", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());break;
				case ERROR_SCREENSHOT: Reporter.warning("<b>" + Message + "</b>",MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());break;
			default:Reporter.info(Message);
		}


	}
}
