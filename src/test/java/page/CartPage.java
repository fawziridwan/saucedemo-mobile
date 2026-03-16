package page;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ApplicationUtils;
import utils.Config;
import utils.YamlReader;

public class CartPage {
    private WebDriver driver;
    private ApplicationUtils applicationUtils;

    private final By checkoutButton = YamlReader.getLocator("CartPage.checkoutButton");

    @SuppressWarnings("rawtypes")
    public CartPage() {
        this.driver = Config.getDriver();
        this.applicationUtils = new ApplicationUtils((AppiumDriver) driver);
    }

    public CheckoutPage proceedToCheckout() {
        applicationUtils.tapElement(checkoutButton, 15L);
        return new CheckoutPage();
    }
}