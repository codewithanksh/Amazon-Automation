package pages;

import enums.WaitTime;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.CommonUtils;

/**
 * Stores all Locators and Action Methods on Signup Page
 */

public class SignUpPage extends CommonUtils {

    private final By CREATE_AMAZON_ACCOUNT_BUTTON = By.cssSelector("#createAccountSubmit");
    private final By NAME = By.id("ap_customer_name");
    private final By REENTER_NAME = By.id("ap_customer_name_pronunciation");
    private final By MOB_NO = By.cssSelector("#ap_phone_number");
    private final By EMAILID = By.cssSelector("#ap_email");
    private final By PASSWORD = By.cssSelector("#ap_password");
    private final By PASSWORD_CHECK = By.cssSelector("#ap_password_check");
    private final By CREATE_ACCT_SUBMIT_BUTTON = By.cssSelector("#continue");

    public void clickOnCreateAccountButton() {
        System.out.println("SIGNIN_PAGE: Clicking the [CREATE_ACCOUNT] button.\n");
        click(CREATE_AMAZON_ACCOUNT_BUTTON);
    }

    public void enterName(String userName){
        System.out.println("CREATEACCOUNT_PAGE: Entering username: " + userName);
        waitForElementToBeVisible(NAME);
        sendKeys(NAME, userName);
    }

    public void reenterName(String userName){
        System.out.println("CREATEACCOUNT_PAGE: Re-entering username: " + userName);
        waitForElementToBeVisible(REENTER_NAME);
        sendKeys(REENTER_NAME, userName);
    }

    public void enterMobileNo(String mobileNo){
        System.out.println("CREATEACCOUNT_PAGE: Entering Mobile No: " + mobileNo);
        waitForElementToBeVisible(NAME);
        sendKeys(MOB_NO, mobileNo);
    }

    public void enterEmail(String emailId){
        System.out.println("CREATEACCOUNT_PAGE: Entering username: " + emailId);
        waitForElementToBeVisible(EMAILID);
        sendKeys(EMAILID, emailId);
    }

    public void enterPassword(String password){
        System.out.println("CREATEACCOUNT_PAGE: Entering password.");
        waitForElementToBeVisible(PASSWORD);
        sendKeys(PASSWORD, password);
    }

    public void enterPasswordForCheck(String password){
        System.out.println("CREATEACCOUNT_PAGE: Entering password. for checking");
        waitForElementToBeVisible(PASSWORD_CHECK);
        sendKeys(PASSWORD_CHECK, password);
    }

    public void clickCreateAccountSubmitButton(){
        System.out.println("CREATEACCOUNT_PAGE: Clicking the [CREATE_ACCOUNT_SUBMIT] button.\n");
        click(CREATE_ACCT_SUBMIT_BUTTON);
        boolean flag = CommonUtils.waitForURLContains(_driver,"cvf", WaitTime.Long_10Sec);

        if (!flag) Assert.fail("Expected Page to move to verifying Puzzle, but something went wrong...Terminating");
    }
}
