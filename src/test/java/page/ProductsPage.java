package page;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ApplicationUtils;
import utils.Config;

public class ProductsPage {
    private WebDriver driver;
    private ApplicationUtils applicationUtils;

    private final By cartIcon = By.xpath("//android.view.ViewGroup[@content-desc='test-Cart']");

    @SuppressWarnings("rawtypes")
    public ProductsPage() {
        this.driver = Config.getDriver();
        this.applicationUtils = new ApplicationUtils((AppiumDriver) driver);
    }

    public void addProductToCart(String productName) {
        // Scroll to the product text first to ensure it's in view
        applicationUtils.scrollToText(productName);
        
        // Use a robust locator that finds the ADD TO CART button within the container of the specific product
        String xpath = String.format("//android.view.ViewGroup[@content-desc='test-Item container' and .//android.widget.TextView[@text='%s']]//android.view.ViewGroup[@content-desc='test-ADD TO CART']", productName);
        applicationUtils.tapElement(By.xpath(xpath), 15L);
    }

    public CartPage goToCart() {
        applicationUtils.tapElement(cartIcon, 15L);
        return new CartPage();
    }
}