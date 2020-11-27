package pages;

import org.openqa.selenium.By;
import utils.CommonUtils;

/** Created by Ankush Sharma on 08/09/2020. */

/**
 * Stores all Locators and Action Methods on Sign In Page
 */
public class SignInPage extends CommonUtils {

    private final By USERNAME = By.cssSelector("#ap_email");
    private final By PASSWORD = By.cssSelector("#ap_password");
    private final By CONTINUE_BUTTON = By.cssSelector("#continue");
    private final By SIGNIN_BUTTON = By.cssSelector("#signInSubmit");

    public void enterUsername(String userName){
        System.out.println("SIGNIN_PAGE: Entering username: " + userName);
        waitForElementToBeVisible(USERNAME);
        sendKeys(USERNAME, userName);
    }

    public void enterPassword(String password){
        System.out.println("SIGNIN_PAGE: Entering password.");
        waitForElementToBeVisible(PASSWORD);
        sendKeys(PASSWORD, password);
    }

    public void clickContinueButton(){
        System.out.println("SIGNIN_PAGE: Clicking the [CONTINUE] button.\n");
        click(CONTINUE_BUTTON);
    }

    public void clickSignInButton(){
        System.out.println("SIGNIN_PAGE: Clicking the [SIGN_IN] button.\n");
        click(SIGNIN_BUTTON);
    }
}
