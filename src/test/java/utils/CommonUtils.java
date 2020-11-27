package utils;

import com.google.common.base.Function;
import enums.WaitTime;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.TestException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankush Sharma on 11/09/2020.
 */

/**
 * Utility Methods
 */
public abstract class CommonUtils {

    private static int timeout = 10;

    public CommonUtils() {
        _driver = DriverUtils.getDriver();
    }

    public static WebDriver _driver;
    public WebDriverWait wait;
    public Actions actions;
    public Select select;

    public void navigateToURL(String URL) {
        try {
            _driver.navigate().to(URL);
        } catch (Exception e) {
            System.out.println("FAILURE: URL did not load: " + URL);
            throw new TestException("URL did not load");
        }
    }

    public void navigateBack() {
        try {
            _driver.navigate().back();
        } catch (Exception e) {
            System.out.println("FAILURE: Could not navigate back to previous page.");
            throw new TestException("Could not navigate back to previous page.");
        }
    }

    public String getPageTitle() {
        try {
            return _driver.getTitle();
        } catch (Exception e) {
            throw new TestException(String.format("Current page title is: %s", _driver.getTitle()));
        }
    }

    public String getCurrentURL() {
        try {
            return _driver.getCurrentUrl();
        } catch (Exception e) {
            throw new TestException(String.format("Current URL is: %s", _driver.getCurrentUrl()));
        }
    }

    public WebElement getElement(By selector) {
        try {
            return _driver.findElement(selector);
        } catch (Exception e) {
            System.out.println(String.format("Element %s does not exist - proceeding", selector));
        }
        return null;
    }

    public String getElementText(By selector) {
        waitUntilElementIsDisplayedOnScreen(selector);
        try {
            return StringUtils.trim(_driver.findElement(selector).getAttribute("value"));
        } catch (Exception e) {
            System.out.println(String.format("Element %s does not exist - proceeding", selector));
        }
        return null;
    }

    public List<WebElement> getElements(By Selector) {
        waitForElementToBeVisible(Selector);
        try {
            return _driver.findElements(Selector);
        } catch (Exception e) {
            throw new NoSuchElementException(String.format("The following element did not display: [%s] ", Selector.toString()));
        }
    }

    public List<String> getListOfElementTexts(By selector) {
        List<String> elementList = new ArrayList<String>();
        List<WebElement> elements = getElements(selector);

        for (WebElement element : elements) {
            if (element == null) {
                throw new TestException("Some elements in the list do not exist");
            }
            if (element.isDisplayed()) {
                elementList.add(element.getText().trim());
            }
        }
        return elementList;
    }

    public void click(By selector) {
        waitForElementToBeClickable(selector);
        WebElement element = getElement(selector);
        try {
            element.click();
        } catch (Exception e) {
            throw new TestException(String.format("The following element is not clickable: [%s]", selector));
        }
    }

    public void scrollToThenClick(By selector) {
        waitForElementToBeVisible(selector);
        WebElement element = _driver.findElement(selector);
        actions = new Actions(_driver);
        try {
            ((JavascriptExecutor) _driver).executeScript("arguments[0].scrollIntoView(true);", element);
            actions.moveToElement(element).perform();
            actions.click(element).perform();
        } catch (Exception e) {
            throw new TestException(String.format("The following element is not found or is not clickable: [%s]", element.toString()));
        }
    }

    public void sendKeys(By selector, String value) {
        waitForElementToBeVisible(selector);
        WebElement element = getElement(selector);
        clearField(element);
        try {
            element.sendKeys(value);
        } catch (Exception e) {
            throw new TestException(String.format("Error in sending [%s] to the following element: [%s %s]", value, selector.toString(),e.fillInStackTrace()));
        }
    }

    public void clearField(WebElement element) {
        try {
            element.clear();
            waitForElementTextToBeEmpty(element);
        } catch (Exception e) {
            System.out.print(String.format("The following element could not be cleared: [%s]", element.getText()));
        }
    }

    public void waitForElementToDisplay(By Selector) {
        WebElement element = getElement(Selector);
        while (!element.isDisplayed()) {
            System.out.println("Waiting for element to display: " + Selector);
            sleep(200);
        }
    }

    public void waitForElementTextToBeEmpty(WebElement element) {
        String text;
        try {
            text = element.getText();
            int maxRetries = 10;
            int retry = 0;
            while ((text.length() >= 1) || (retry < maxRetries)) {
                retry++;
                text = element.getText();
            }
        } catch (Exception e) {
            System.out.print(String.format("The following element could not be cleared: [%s]", element.getText()));
        }

    }

    public void waitForElementToBeVisible(By selector) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.presenceOfElementLocated(selector));
        } catch (Exception e) {
            throw new NoSuchElementException(String.format("The following element was not visible: %s", selector));
        }
    }

    public void waitUntilElementIsDisplayedOnScreen(By selector) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        } catch (Exception e) {
            throw new NoSuchElementException(String.format("The following element was not visible: %s ", selector));
        }
    }

    public void waitForElementToBeClickable(By selector) {
        try {
            wait = new WebDriverWait(_driver, timeout);
            wait.until(ExpectedConditions.elementToBeClickable(selector));
        } catch (Exception e) {
            throw new TestException("The following element is not clickable: " + selector);
        }
    }

    public void sleep(final long millis) {
        System.out.println((String.format("sleeping %d ms", millis)));
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void selectIfOptionTextContains(By selector, String searchCriteria) {

        waitForElementToBeClickable(selector);
        Select dropdown = new Select(getElement(selector));

        List<WebElement> options = dropdown.getOptions();

        String optionText = "";

        if (options == null) {
            throw new TestException("Options for the dropdown list cannot be found.");
        }

        for (WebElement option : options) {

            optionText = option.getText().trim();
            boolean isOptionDisplayed = option.isDisplayed();

            if (optionText.contains(searchCriteria) && isOptionDisplayed) {
                try {
                    dropdown.selectByVisibleText(optionText);
                    break;
                } catch (Exception e) {
                    throw new NoSuchElementException(String.format("The following element did not display: [%s] ", selector.toString()));
                }
            }
        }
    }

    public void selectIfOptionTextEquals(By selector, String searchCriteria) {

        waitForElementToBeClickable(selector);
        Select dropdown = new Select(getElement(selector));

        List<WebElement> options = dropdown.getOptions();

        String optionText = "";

        if (options == null) {
            throw new TestException("Options for the dropdown list cannot be found.");
        }

        for (WebElement option : options) {

            optionText = option.getText().trim();
            boolean isOptionDisplayed = option.isDisplayed();

            if (optionText.equals(searchCriteria) && isOptionDisplayed) {
                try {
                    dropdown.selectByVisibleText(optionText);
                    break;
                } catch (Exception e) {
                    throw new NoSuchElementException(String.format("The following element did not display: [%s] ", selector.toString()));
                }
            }
        }
    }

    public List<String> getDropdownValues(By selector) {

        waitForElementToDisplay(selector);
        Select dropdown = new Select(getElement(selector));
        List<String> elementList = new ArrayList<String>();

        List<WebElement> allValues = dropdown.getOptions();

        if (allValues == null) {
            throw new TestException("Some elements in the list do not exist");
        }

        for (WebElement value : allValues) {
            if (value.isDisplayed()) {
                elementList.add(value.getText().trim());
            }
        }
        return elementList;
    }

    public void displayInformationAlert(String message, int waitTimeInSeconds) {
        JavascriptExecutor js = (JavascriptExecutor)_driver ;
        js.executeScript("alert(\""+message+"\");");
        Alert alert = _driver.switchTo().alert();
        sleep(waitTimeInSeconds*1000);
        try {
        alert.accept();
        } catch (NoAlertPresentException e1){}
        System.out.println("JavaScript alert with [message]: "+message+" is displayed");
    }

    public String returnMeEnteredText(String message, int waitTime) {
        JavascriptExecutor js = (JavascriptExecutor)_driver ;
        js.executeScript(" prompt(\"Enter code or message into this textbox\", \"Sample Text\");");
        sleep(waitTime*1000);
        return getAlertText();
    }

    public String getAlertText() {
        wait = new WebDriverWait(_driver, timeout);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = _driver.switchTo().alert();
        return alert.getText();
    }

    public boolean acceptAlert() {
        wait = new WebDriverWait(_driver, timeout);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = _driver.switchTo().alert();
        alert.accept();
        return true;
    }

    public boolean dismissAlert() {
        wait = new WebDriverWait(_driver, timeout);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = _driver.switchTo().alert();
        alert.dismiss();
        return true;
    }

    public void waitForAlertToAppear(String message, int timeOutInSec) {
        try {
            wait = new WebDriverWait(_driver, timeOutInSec);
            wait.until(ExpectedConditions.alertIsPresent());

            String alertMessage = _driver.switchTo().alert().getText();

            int maxRetries = 50, retry = 0;

            while (!alertMessage.equalsIgnoreCase(message)) {

                if (retry >= maxRetries) { break; }

                retry++;
                alertMessage = _driver.switchTo().alert().getText();
            }

            if (retry<maxRetries)
                dismissAlert();

        } catch (Exception e) {
            throw new TestException(" Alert with text "+message+" is not found: " , e);
        }
    }

    public void switchToDefaultContent() {
        try{
            _driver.switchTo().defaultContent();
        }catch(Exception p ) {
            throw new WebDriverException("Unable to switch to default content");
        }
    }

    public static boolean waitForTitleContains(WebDriver driver, String titlePattern, WaitTime waitTime) {
        try {
            return (new WebDriverWait(driver, waitTime.val())).until(titleContains(titlePattern));
        } catch (WebDriverException e) {
            return false;
        }
    }

    public static boolean waitForTitleNotContains(WebDriver driver, String titlePattern, WaitTime waitTime) {
        try {
            return (new WebDriverWait(driver, waitTime.val())).until(titleNotContains(titlePattern));
        } catch (WebDriverException e) {
            return false;
        }
    }

    public static boolean waitForURLContains(WebDriver driver, String urlPattern, WaitTime waitTime) {
        try {
            return (new WebDriverWait(driver, waitTime.val())).until(urlContains(urlPattern));
        } catch (WebDriverException e) {
            return false;
        }
    }

    public static boolean waitForURLNotContains(WebDriver driver, String urlPattern, WaitTime waitTime) {
        try {
            return (new WebDriverWait(driver, waitTime.val())).until(urlNotContains(urlPattern));
        } catch (WebDriverException e) {
            return false;
        }
    }

    public static ExpectedCondition<Boolean> titleContains(final String title) {
        return new ExpectedCondition<Boolean>() {
            private String currentTitle = null;

            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    currentTitle = driver.getTitle();
                    return contains(currentTitle, title);
                } catch (WebDriverException e) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return String.format("Title pattern is '%s', current title is '%s'", title, currentTitle);
            }
        };
    }

    public static ExpectedCondition<Boolean> titleNotContains(final String title) {
        return new ExpectedCondition<Boolean>() {
            private String currentTitle = null;

            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    currentTitle = driver.getTitle();
                    return !title.equals(currentTitle) && !contains(currentTitle, title);
                } catch (WebDriverException e) {
                    return false;
                }
            }

            @Override
            public String toString() {
                return String.format("Title pattern is '%s', current title is '%s'", title, currentTitle);
            }
        };
    }

    public static boolean textContains(WebDriver driver, final String text, final By by) {
        try {
            return (new WebDriverWait(driver, WaitTime.Short_3Sec.val())).until(textContains(text, by));
        } catch (WebDriverException e) {
            return false;
        }
    }

    public static ExpectedCondition<Boolean> textContains(final String text, final By by) {
        return new ExpectedCondition<Boolean>() {
            private String currentText = null;

            @Override
            public Boolean apply(WebDriver driver) {
                currentText = driver.findElement(by).getText();
                return contains(currentText, text);
            }

            @Override
            public String toString() {
                return String.format("Text Pattern is '%s', current text is '%s'", text, currentText);
            }
        };
    }

    private static ExpectedCondition<Boolean> urlContains(final String url) {
        return new ExpectedCondition<Boolean>() {
            private String currentUrl = null;

            @Override
            public Boolean apply(WebDriver driver) {
                currentUrl = driver.getCurrentUrl();
                return contains(currentUrl, url);
            }

            @Override
            public String toString() {
                return String.format("Url Pattern is '%s', current url is '%s'", url, currentUrl);
            }
        };
    }

    private static ExpectedCondition<Boolean> urlNotContains(final String url) {
        return new ExpectedCondition<Boolean>() {
            private String currentUrl = null;

            @Override
            public Boolean apply(WebDriver driver) {
                currentUrl = driver.getCurrentUrl();
                return !contains(currentUrl, url);
            }

            @Override
            public String toString() {
                return String.format("Url Pattern is '%s', current url is '%s'", url, currentUrl);
            }
        };
    }


    public static boolean present(WebDriver driver, By by, WaitTime waitTime) {
        return wait(driver, Indicator.Present, by, waitTime);
    }

    public static boolean visible(WebDriver driver, By by, WaitTime waitTime) {
        return wait(driver, Indicator.Visible, by, waitTime);
    }

    public static boolean inVisible(WebDriver driver, By by, WaitTime waitTime) {
        return wait(driver, Indicator.Invisible, by, waitTime);
    }

    public static boolean clickable(WebDriver driver, By by, WaitTime waitTime) {
        return wait(driver, Indicator.Clickable, by, waitTime);
    }

    public static WebElement findElement(WebDriver driver, By by, WaitTime waitTime) {
        return (new WebDriverWait(driver, waitTime.val())).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public enum Indicator {
        Present,
        Visible,
        Invisible,
        Clickable
    }

    private static boolean wait(WebDriver driver, Indicator indicator, By by, WaitTime waitTime) {
        try {
            switch (indicator) {
                case Present:
                    return (new WebDriverWait(driver, waitTime.val())).until(ExpectedConditions.presenceOfElementLocated(by)) != null;
                case Visible:
                    return (new WebDriverWait(driver, waitTime.val())).until(ExpectedConditions.visibilityOfElementLocated(by)) != null;
                case Invisible:
                    return (new WebDriverWait(driver, waitTime.val())).until(ExpectedConditions.invisibilityOfElementLocated(by));
                case Clickable:
                    return (new WebDriverWait(driver, waitTime.val())).until(ExpectedConditions.elementToBeClickable(by)) != null;
            }

            throw new IllegalArgumentException("Unsupported Indicator: " + indicator);
        } catch (WebDriverException e) {
            return false;
        }
    }

    private static boolean contains(String source, String... strings) {
        if (source.isEmpty()) {
            return false;
        }
        for (String s : strings) {
            if (!source.toUpperCase().contains(s.toUpperCase())) {
                return false;
            }
        }
        return true;
    }

    public static boolean waitForPageChange(WebDriver driver, WaitTime waitTime) {
        String currentURL = driver.getCurrentUrl();
        return waitForURLNotContains(driver, currentURL, waitTime);
    }



    public static WebElement ignoreStaleElementException(WebDriver driver,By locator) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(100))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class) ;

        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

    }

    public static boolean waitTillTextIsDisplayed(WebDriver driver, By locator, WaitTime waitTime) {
        return new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                try {
                    if(locator.toString().contains("input")) {
                        return driver.findElement(locator).getAttribute("value").trim().length() != 0 ;
                    } else {
                        return driver.findElement(locator).getAttribute("value") != null ?
                                driver.findElement(locator).getAttribute("value").trim().length() != 0 :
                                driver.findElement(locator).getText().trim().length() != 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                    return false;
                }
            }
        });
    }

    public static boolean waitForPageToLoad(WebDriver driver , long timeOutInSeconds) {

        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }

        };

        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            return wait.until(expectation);
        } catch (Throwable error) {
            System.out.println("Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
            Assert.assertFalse(true, "Timeout waiting for Page Load Request to complete.");
            return false;
        }
    }

    public static WebElement fluentWait(WebDriver driver,final By locator , final int waitTime , final int poolingTime) {
        WebElement element = null;
        try {
            FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(waitTime)).pollingEvery(Duration.ofSeconds(poolingTime)).ignoring(NoSuchElementException.class);
            element = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(locator);
                }
            });
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return element;
    }

    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}