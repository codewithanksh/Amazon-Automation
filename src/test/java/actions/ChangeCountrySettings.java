package actions;

import org.testng.Assert;
import pages.AddressPage;
import pages.DataCapturePage;
import pages.HomePage;
import pages.YourAccountPage;

import java.util.HashMap;

/**
 * Includes logic to change country settings
 */
public class ChangeCountrySettings {

    public static HashMap<String, String> formDataMap;

    /**
     * Application Flow to change country settings is written here
     */
    public void changeCountrySettings() {
        HomePage homePage = new HomePage();
        YourAccountPage yourAccountPage = new YourAccountPage();
        AddressPage addressPage = new AddressPage(DataCapturePage.formDataMap);

        homePage.navigateToSignInPage();
        yourAccountPage.clickOnContentAndDevices();
        addressPage.clickOnPreferencesTab();
        addressPage.clickOnCountrySection();
        addressPage.clickOnCountryChange();
        addressPage.fillAddressDetails();
        addressPage.clickUpdate();
        String countryName = addressPage.getCountryName();

        /* Assertion to check if country name is changed properly in code*/
        Assert.assertEquals(DataCapturePage.formDataMap.get("country").toLowerCase(),
                countryName.toLowerCase(), "Country Name Mismatched");

        System.out.println("COUNTRY NAME CHANGED SUCCESSFULLY TO "+countryName);
    }
}
