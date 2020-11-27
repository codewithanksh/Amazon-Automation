package pages;

import enums.Url;
import enums.WaitTime;
import org.openqa.selenium.By;
import utils.CommonUtils;

/** Created by Ankush Sharma on 08/09/2020. */

/**
 * Stores all Locators and Action Methods on Home Page
 */
public class HomePage extends CommonUtils {
    private final By YOUR_ACCOUNT = By.id("nav-link-accountList");
    private final By SHOPPING_CART_ICON = By.cssSelector("#nav-cart");
    private final By SHOPPING_CART_COUNT = By.cssSelector("#nav-cart > #nav-cart-count");

    public HomePage(){}

    public void navigateToHomePage(String url) {
//        String url = Url.BASEURL.getURL();
        System.out.println("Navigating to Amazon.com: " + url);
        navigateToURL(url);
    }

    public void navigateToSignInPage(){
        System.out.println("HOME_PAGE: Selecting [YOUR_ACCOUNT] in navigation bar.");
        CommonUtils.waitForTitleContains(_driver,"Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in", WaitTime.Longest_30Sec);
        scrollToThenClick(YOUR_ACCOUNT);
        System.out.println("HOME_PAGE: Navigating to the SIGNIN_PAGE.\n");
    }

    public void signOutWithSignOutLink(){
        String url = Url.DATACAPTURE_FORM_URL.getURL() + Url.SIGNOUT.getURL();
        navigateToURL(url);
    }
}
