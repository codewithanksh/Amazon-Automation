package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import utils.CommonUtils;

import java.util.HashMap;

public class AddressPage extends CommonUtils {

    private final By TAB_PREFERENCES = By.cssSelector("#kindle_myk_your_settings");
    private final By BTN_COUNTRY_SETTINGS = By.cssSelector("#country > a");
    private final By BTN_COUNTRY_CHANGE = By.xpath("//a[@id='country_change_myx ']//button");

    private final By IN_ADDRESS1 = By.xpath("//input[@id='adr_AddressLine1']");
    private final By IN_ADDRESS2 = By.xpath("//input[@id='adr_AddressLine2']");
    private final By IN_CITY = By.xpath("//input[@id='adr_City']");
    private final By IN_STATE = By.xpath("//input[@id='adr_StateOrRegion']");
    private final By IN_PINCODE = By.xpath("//input[@id='adr_PostalCode']");
    private final By DRPDWN_COUNTRY = By.xpath("//select[@ng-model='selectedCountryCode']");
    private final By IN_MOBILE_NO = By.xpath("//input[@id='adr_PhoneNumber']");
    private final By BTN_UPDATE = By.xpath("//a[@id='dialogButton_ok_myx ']//button");

    private final By COUNTRY_NAME_SAVED = By.xpath("//a[@id='country_change_myx ']/preceding-sibling::div/div[2]");

    HashMap<String,String> formDataMap;

    public AddressPage(HashMap formDataMap) {
        this.formDataMap = formDataMap;
    }

    public void navigateToHomePage(String url) {
        //String url = Url.BASEURL.getURL();
        System.out.println("Navigating to Amazon.com: " + url);
        navigateToURL(url);
    }


    public void clickOnPreferencesTab() {
        System.out.println("Clicking on Preferences TAB");
        scrollToThenClick(TAB_PREFERENCES);
    }

    public void clickOnCountrySection() {
        System.out.println("Clicking on Country Setting Section");
        click(BTN_COUNTRY_SETTINGS);
    }

    public void clickOnCountryChange() {
        System.out.println("Clicking on Country Change Section");
        scrollToThenClick(BTN_COUNTRY_CHANGE);
    }

    public void fillAddressDetails() {

        sendKeys(IN_ADDRESS1, formDataMap.get("address_1"));
        sendKeys(IN_ADDRESS2, formDataMap.get("address_2"));
        sendKeys(IN_CITY, formDataMap.get("city"));
        sendKeys(IN_STATE, "State");
        sendKeys(IN_PINCODE, formDataMap.get("zip"));
        selectIfOptionTextContains(DRPDWN_COUNTRY, formDataMap.get("country"));
        sendKeys(IN_MOBILE_NO, formDataMap.get("mob"));

    }

    public void clickUpdate() {
        System.out.println("Clicking on [Update] Button");
        click(BTN_UPDATE);
    }

    public String getCountryName() {
        return getElementText(COUNTRY_NAME_SAVED);
    }
}
