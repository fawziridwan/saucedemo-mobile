package page;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ApplicationUtils;
import utils.Config;
import utils.YamlReader;

public class LoginPage {
    WebDriverWait wait;
    private WebDriver driver;
    public ApplicationUtils applicationUtils;

    private final By usernameField = YamlReader.getLocator("LoginPage.usernameField");
    private final By passwordField = YamlReader.getLocator("LoginPage.passwordField");
    private final By loginButton = YamlReader.getLocator("LoginPage.loginButton");
    private final By logoSwaglab = YamlReader.getLocator("LoginPage.logoSwaglab");
    private final By titleProduct = YamlReader.getLocator("LoginPage.titleProduct");

    // message validation
    private final By errorMessageContainer = YamlReader.getLocator("LoginPage.errorMessageContainer");

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