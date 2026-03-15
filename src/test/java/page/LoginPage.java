package page;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ApplicationUtils;
import utils.Config;

public class LoginPage {
    WebDriverWait wait;
    private WebDriver driver;
    public ApplicationUtils applicationUtils;

    private final By usernameField = By.xpath("//android.widget.EditText[@content-desc='test-Username']");
    private final By passwordField = By.xpath("//android.widget.EditText[@content-desc='test-Password']");
    private final By loginButton = By.xpath("//android.widget.TextView[@text='LOGIN']");
    private final By logoSwaglab = By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ImageView[2]");
    private final By titleProduct = By.xpath("//android.widget.TextView[@text='PRODUCTS']");

    // message validation
    private final By errorMessageContainer = By.xpath("//android.view.ViewGroup[@content-desc='test-Error message']//android.widget.TextView");

    @SuppressWarnings("rawtypes")
    public LoginPage() {
        this.driver = Config.getDriver();
        this.applicationUtils = new ApplicationUtils((AppiumDriver) driver);
        wait = new WebDriverWait(driver, 15);
    }

    public void isSwagLabsLogoDisplayed() {
        applicationUtils.getElementIsDisplayed(logoSwaglab);
    }

    public void isProductsTitleDisplayed() {
        applicationUtils.getElementIsDisplayed(titleProduct);
    }

    public boolean isLoginScreenDisplayed() {
        try {
            return driver.findElement(usernameField).isDisplayed() &&
                    driver.findElement(passwordField).isDisplayed() &&
                    driver.findElement(loginButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterUsername(String username) {
        Assert.assertTrue(applicationUtils.enterValueInTextBox(username, usernameField));
    }

    public void enterPassword(String password) {
        Assert.assertTrue(applicationUtils.enterValueInTextBox(password, passwordField));
    }

    public void tapLoginButton() {
        Assert.assertTrue(applicationUtils.tapElement(loginButton, null));
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        tapLoginButton();
    }

    private String getErrorMessage() {
        // Try to find the error message
        try {
            return applicationUtils.getElementText(errorMessageContainer);
        } catch (Exception e) {
            return "";
        }
    }

    public void validateMessageError(String expectedMessage) {
        String actualMessage = getErrorMessage();
        Assert.assertEquals(actualMessage, expectedMessage,
                "Error message validation failed. Expected: '" + expectedMessage +
                        "' but found: '" + actualMessage + "'");
    }
}