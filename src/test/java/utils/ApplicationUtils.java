package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationUtils {
    private static final Logger log = LoggerFactory.getLogger(ApplicationUtils.class);
    private static final long DEFAULT_TIMEOUT = 10;
    private static long globalTimeOut;
    private WebDriverWait wait;

    static {
        String timeoutProp = System.getProperty("timeout");
        globalTimeOut = (timeoutProp != null) ? Long.parseLong(timeoutProp) : DEFAULT_TIMEOUT;
    }

    private AppiumDriver driver;

    public ApplicationUtils(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void waitFor(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            log.error("Sleep interrupted", ie);
        }
    }

    public boolean waitForElementToBePresent(By by, long second) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, second);
            // This returns the element once it is present in the DOM
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return element.isDisplayed();
        } catch (Exception e) {
            log.warn("Element not present or visible after {} seconds: {}", second, by);
            return false;
        }
    }


    public boolean enterValueInTextBox(String text, By by) {
        boolean flag = false;
        if (waitForElementToBePresent(by, globalTimeOut)) {
            try {
                if (driver.findElement(by).isDisplayed()) {
                    driver.findElement(by).click();
                    driver.findElement(by).clear();
                    driver.findElement(by).sendKeys(text);
                    flag = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public String getElementText(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return driver.findElement(by).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public Boolean getElementIsDisplayed(By by) {
        return driver.findElement(by).isDisplayed();
    }

    /**
     * Clicks an element using XPath containing the given text.
     *
     * @param text  The text to be used in the XPath expression.
     */
    public void clickByXPathContainingText(String text) {
        try {
            // Construct XPath and find the element
            String xpath = String.format("//*[contains(@text, '%s') or contains(@label, '%s')]", text, text);
            WebElement element = driver.findElement(By.xpath(xpath));
            element.click();
            System.out.println("Clicked element with text: " + text);
        } catch (Exception e) {
            System.err.println("Failed to click element with text: " + text);
            e.printStackTrace();
        }
    }

    public boolean tapElement(By currentPage, Long second) {
        try {
            if (waitForElementToBePresent(currentPage, second == null ? globalTimeOut : second)) {
                driver.findElement(currentPage).click();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Scrolls to an element with the given text using Android UI Automator.
     * @param text The text to scroll to.
     */
    public void scrollToText(String text) {
        try {
            driver.findElement(io.appium.java_client.MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + text + "\").instance(0))"));
        } catch (Exception e) {
            log.warn("Failed to scroll to text: {}", text);
        }
    }

    /**
     * Hides the keyboard if it is displayed.
     */
    public void hideKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            log.debug("Keyboard already hidden or not supported");
        }
    }
}
