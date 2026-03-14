package page;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Config;

public class LoginPage {
    WebDriverWait wait;
    private WebDriver driver;

    private final By usernameField = By.xpath("//android.widget.EditText[@content-desc='test-Username']");
    private final By passwordField = By.xpath("//android.widget.EditText[@content-desc='test-Password']");
    private final By loginButton = By.xpath("//android.widget.TextView[@text='LOGIN']");
    private final By logoSwaglab = By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ImageView[2]");
    private final By titleProduct = By.xpath("//android.widget.TextView[@text='PRODUCTS']");

    public LoginPage() {
        this.driver = Config.getDriver();
        wait = new WebDriverWait(driver, 15);
    }

    public boolean isSwagLabsLogoDisplayed() {
        try {
            return driver.findElement(logoSwaglab).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProductsTitleDisplayed() {
        try {
            return driver.findElement(titleProduct).isDisplayed();
        } catch (Exception e) {
            return false;
        }
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
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void tapLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        tapLoginButton();
    }
}