package page;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ApplicationUtils;
import utils.Config;
import utils.YamlReader;

public class CheckoutPage {
    private WebDriver driver;
    private ApplicationUtils applicationUtils;

    private final By firstNameField = YamlReader.getLocator("CheckoutPage.firstNameField");
    private final By lastNameField = YamlReader.getLocator("CheckoutPage.lastNameField");
    private final By zipCodeField = YamlReader.getLocator("CheckoutPage.zipCodeField");
    private final By continueButton = YamlReader.getLocator("CheckoutPage.continueButton");
    private final By finishButton = YamlReader.getLocator("CheckoutPage.finishButton");
    private final By completionMessage = YamlReader.getLocator("CheckoutPage.completionMessage");

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