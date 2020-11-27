package testcases;


import actions.ChangeCountrySettings;
import actions._NewAccount;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.DataCapturePage;
import utils.DriverUtils;
import utils.Report;

/** Created by Ankush Sharma on 08/09/2020. */
public class NewAccount {

  public WebDriver driver;
  private static int iteration = 1;

  @BeforeClass
  /**
   * Initializes Webdriver and Reporter Objects
   */
  public void setUp() {
    Report.initializeExtentReporter();
    driver = DriverUtils.getDriver();
  }

  /**
   * Template Methods
   */
  @BeforeTest
  public void beforeTest() {
    //No Actions done
  }

  /**
   * Template Methods
   */
  @BeforeMethod(alwaysRun = true)
  public void beforeMethod() {
    //No Actions done
  }


  /**
   * use following method to create a new Account
   * All the inputs needed would be captured as part of formDataMap variable
   * Once this method executes successfully,then next set of actions would take place
   */
  @Test()
  public void createNewAmazonAccount() {
    captureFormData();
    _NewAccount newAccount = new _NewAccount();
    newAccount.navigateToHomePage("https://"+DataCapturePage.formDataMap.get("amazon_url"));
    newAccount.signUpAs(DataCapturePage.formDataMap);
  }

  /**
   * Use following method to signin to a Account Account
   * This methods depends upon Sign Up Action. If it passed then only Sign In Action would happen
   * Post Sign Country setting change would take place
   */
  @Test(dependsOnMethods = { "createNewAmazonAccount" })
  public void signInToAmazonAccount() {
    _NewAccount newAccount = new _NewAccount();
    ChangeCountrySettings changeCountry = new ChangeCountrySettings();
    newAccount.navigateToHomePage("https://"+DataCapturePage.formDataMap.get("amazon_url"));
    newAccount.loginAs(DataCapturePage.formDataMap.get("name"), DataCapturePage.formDataMap.get("password"));
    changeCountry.changeCountrySettings();
  }

  /**
   * To log test case run status in console
   */
  @AfterMethod(alwaysRun = true)
  public void afterMethod(ITestResult iTestResult) {
    iteration++;
    System.out.println("[Test Case Result] :"+iTestResult.getStatus());
  }

  /**
   * Template Methods
   */
  @AfterMethod(alwaysRun = true)
  public void afterMethod(){
    //No Actions done
  }

  /**
   * To Kill Browser Instance
   */
  @AfterClass(alwaysRun = true)
  public void tearDown() {
    Report.flushExtentReports();
    driver.quit();
  }

  /**
   * Signup form Data required is maintained in this method
   */
  private void captureFormData() {
    DataCapturePage dataCapturePage = new DataCapturePage();
    dataCapturePage.navigateToDataCapturePage();
    dataCapturePage.readFormData();
  }

  public static void main(String[] args) {
    TestNG testng = new TestNG();
    testng.setTestClasses(new Class[] {NewAccount.class});
    testng.run();
  }
}
