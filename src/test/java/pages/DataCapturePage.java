package pages;

import enums.Url;
import org.openqa.selenium.By;
import utils.CommonUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * All operations wrt to form Data Capture for signup is written here
 */
public class DataCapturePage extends CommonUtils {

  private final By AMAZON_URL = By.cssSelector("select#exampleFormControlSelect.form-control");
  private final By NAME = By.id("inputName");
  private final By EMAIL = By.id("inputEmail");
  private final By PASSWORD = By.id("inputPassword");
  private final By ADDRESS_1 = By.id("inputAddress");
  private final By ADDRESS_2 = By.id("inputAddress2");
  private final By CITY = By.id("inputCity");
  private final By COUNTRY = By.id("inputState");
  private final By ZIP = By.id("inputZip");
  private final By C_CODE = By.id("inputCountryCode");
  private final By MOB = By.id("inputPhoneNo");

  public static HashMap<String, String> formDataMap = new LinkedHashMap<String, String>();

  public DataCapturePage() {}

  /**
   * This method is used to navigate to Data Capture Page
   * & would open an index.html file in browser
   */
  public void navigateToDataCapturePage() {
    String url = Url.DATACAPTURE_FORM_URL.getURL();
    System.out.println("Navigating to Data Capture form: " + url);
    navigateToURL(url);
  }

  /**
   * This method captures all the data input by user inside DataCapture Page
   * This Data would be used while Singup
   * @return HashMap<Key,Value>
   */
  public HashMap<String, String> readFormData() {

    System.out.println("[Page Title]: "+_driver.getTitle());
    waitForAlertToAppear("Success !! All the details have been successfully processed", 300);
    String amazon_url = getElementText(AMAZON_URL);
    String name = getElementText(NAME);
    String email = getElementText(EMAIL);
    String password = getElementText(PASSWORD);
    String address_1 = getElementText(ADDRESS_1);
    String address_2 = getElementText(ADDRESS_2);
    String city = getElementText(CITY);
    String country = getElementText(COUNTRY);
    String zip = getElementText(ZIP);
    String c_code = getElementText(C_CODE);
    String mob = getElementText(MOB);

    formDataMap.put("amazon_url", amazon_url);
    formDataMap.put("name", name);
    formDataMap.put("email", email);
    formDataMap.put("password", password);
    formDataMap.put("address_1", address_1);
    formDataMap.put("address_2", address_2);
    formDataMap.put("city", city);
    formDataMap.put("country", country);
    formDataMap.put("zip", zip);
    formDataMap.put("c_code", c_code);
    formDataMap.put("mob", mob);

    System.out.println("[FORM DATA READ IS ]" + formDataMap.toString());

    return formDataMap;
  }
}
