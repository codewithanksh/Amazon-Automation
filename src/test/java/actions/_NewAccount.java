package actions;

import org.testng.TestException;
import pages.*;

import java.util.HashMap;

/** Created by Ankush Sharma on 08/09/2020. */
public class _NewAccount {

    public static HashMap<String, String> formDataMap;

    public void navigateToHomePage(String url){
        HomePage homePage = new HomePage();
        homePage.navigateToHomePage(url);
        this.formDataMap = DataCapturePage.formDataMap;
    }

    /**
     * following method would perform Signup action for the User
     * @param formDataMap - This a Map<Key,Value> collection of all the user inputs provided in Data Capture form
     * @param formDataMap - This would be used to input values for Different fields
     */
    public void signUpAs(HashMap<String, String> formDataMap) {
        HomePage homePage = new HomePage();
        SignUpPage signUp = new SignUpPage();
        homePage.navigateToSignInPage();
        signUp.clickOnCreateAccountButton();

        // Need customization as per the User locale
        int len = formDataMap.get("amazon_url").split("\\.").length;
        String locale = formDataMap.get("amazon_url").split("\\.")[len - 1];
        System.out.println("Creating new account for [" + locale + "] locale");
        switch (locale) {
            case "br":
            case "ca":
            case "fr":
            case "de":
            case "it":
            case "mx":
            case "es":
            case "uk":
            case "com":
                signUp.enterName(formDataMap.get("name"));
                signUp.enterEmail(formDataMap.get("email"));
                signUp.enterPassword(formDataMap.get("password"));
                signUp.enterPasswordForCheck(formDataMap.get("password"));
                break;
            case "jp":
                signUp.enterName(formDataMap.get("name"));
                signUp.reenterName(formDataMap.get("name"));
                signUp.enterEmail(formDataMap.get("email"));
                signUp.enterPassword(formDataMap.get("password"));
                signUp.enterPasswordForCheck(formDataMap.get("password"));
                break;
            case "in":
                signUp.enterName(formDataMap.get("name"));
                signUp.enterMobileNo(formDataMap.get("mob"));
                signUp.enterEmail(formDataMap.get("email"));
                signUp.enterPassword(formDataMap.get("password"));
                break;
            default:
                throw new TestException("Failing Test..Unable to extract locale information");
        }

        signUp.clickCreateAccountSubmitButton();
        signUp.displayInformationAlert("Browser would wait for 1 Minute ! Please solve the puzzle manually to proceed",60);
    }


    /**
     * Login user with provided credentials during initial form Data Capture
     * @param username - Username to login [Would be read from TestScript]
     * @param password - Password to login [Would be read from TestScript]
     * Based on the country selected, appropriate Locale website URL would be selected
     */
    public void loginAs(String username, String password){
        HomePage homePage = new HomePage();
        SignInPage signIn = new SignInPage();
        homePage.navigateToSignInPage();

        // Need customization as per the User locale
        int len = formDataMap.get("amazon_url").split("\\.").length;
        String locale = formDataMap.get("amazon_url").split("\\.")[len - 1];
        System.out.println("Creating new account for [" + locale + "] locale");
        //Based on locale selection, login form fields can change and that is controlled by Switch Statement
        switch (locale) {
            case "br":
            case "ca":
            case "fr":
            case "de":
            case "it":
            case "mx":
            case "es":
            case "uk":
            case "com":
            case "jp":
            case "in":
                signIn.enterUsername(formDataMap.get("email"));
                signIn.clickContinueButton();
                signIn.enterPassword(formDataMap.get("password"));
                break;
            default:
                throw new TestException("Failing Test..Unable to extract locale information");
        }

/*
        signUp.clickCreateAccountSubmitButton();
        signUp.displayInformationAlert("Browser would wait for 1 Minute ! Please solve the puzzle manually to proceed",5);
*/
        signIn.clickSignInButton();
        homePage.navigateToSignInPage();
    }



    public boolean checkMatchingValues(String testHeading, Object actualValue, Object expectedValue) {
        String successMessage = "\t* The Expected and Actual Values match. (PASS)\n";
        String failureMessage = "\t* The Expected and Actual Values do not match! (FAIL)\n";

        boolean doesPriceMatch = false;

        System.out.println(testHeading);
        System.out.println("\t* Expected Value: " + expectedValue);
        System.out.println("\t* Actual Value: " + actualValue);

        if (actualValue.equals(expectedValue)) {
            System.out.println(successMessage);
            doesPriceMatch = true;
        } else {
            System.out.println(failureMessage);
            doesPriceMatch = false;
        }
        return doesPriceMatch;
    }

    /**
     * Compares actual and expected values, and sets up the results in one line item.
     * @param fieldName: What you are comparing, such as "unitPrice".
     * @param actualValue: The value you wish to compare.
     * @param expectedValue: The value you hope the actual value will be.
     * @return
     */
    private String outputPassOrFailOnFieldComparison(String fieldName, String actualValue, String expectedValue){
        if (actualValue.equals(expectedValue)){
            return "\t* " + fieldName + ": '" + actualValue + "' (PASS)";
        } else {
            return "\t* " + fieldName + ": '" + actualValue + "' : Should be: '" + expectedValue + "' (FAIL)";
        }
    }

}
