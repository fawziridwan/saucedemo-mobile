package page;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ApplicationUtils;
import utils.Config;

public class CheckoutPage {
    private WebDriver driver;
    private ApplicationUtils applicationUtils;

    private final By firstNameField = By.xpath("//android.widget.EditText[@content-desc='test-First Name']");
    private final By lastNameField = By.xpath("//android.widget.EditText[@content-desc='test-Last Name']");
    private final By zipCodeField = By.xpath("//android.widget.EditText[@content-desc='test-Zip/Postal Code']");
    private final By continueButton = By.xpath("//android.view.ViewGroup[@content-desc='test-CONTINUE']");
    private final By finishButton = By.xpath("//android.view.ViewGroup[@content-desc='test-FINISH']");
    private final By completionMessage = By.xpath("//android.widget.TextView[@text='CHECKOUT: COMPLETE!']");

    @SuppressWarnings("rawtypes")
    public CheckoutPage() {
        this.driver = Config.getDriver();
        this.applicationUtils = new ApplicationUtils((AppiumDriver) driver);
    }

    public void enterFirstName(String firstName) {
        applicationUtils.enterValueInTextBox(firstName, firstNameField);
    }

    public void enterLastName(String lastName) {
        applicationUtils.enterValueInTextBox(lastName, lastNameField);
    }

    public void enterZipCode(String zipCode) {
        applicationUtils.enterValueInTextBox(zipCode, zipCodeField);
    }

    public void clickContinue() {
        applicationUtils.tapElement(continueButton, 15L);
    }

    public void clickFinish() {
        applicationUtils.tapElement(finishButton, 15L);
    }

    public boolean isOrderComplete() {
        return applicationUtils.getElementIsDisplayed(completionMessage);
    }

    public void enterCheckoutInfo(String firstName, String lastName, String zipCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterZipCode(zipCode);
        applicationUtils.hideKeyboard();
        clickContinue();
    }
}