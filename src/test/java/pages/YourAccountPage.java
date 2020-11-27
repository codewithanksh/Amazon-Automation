package pages;

import org.openqa.selenium.By;
import utils.CommonUtils;

/**
 * Stores all Locators and Action Methods on Your Account Page
 */
public class YourAccountPage extends CommonUtils {

    private final By CONTENT_AND_DEVICES = By.xpath("(//ul[@class='a-unordered-list a-nostyle a-vertical'])[1]/li[2]//a");

    public YourAccountPage(){}

    public void clickOnContentAndDevices() {
        System.out.println("Clicking on Content & Devices");
        scrollToThenClick(CONTENT_AND_DEVICES);
    }
}
