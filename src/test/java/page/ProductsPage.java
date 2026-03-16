package page;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ApplicationUtils;
import utils.Config;
import utils.YamlReader;

public class ProductsPage {
    private WebDriver driver;
    private ApplicationUtils applicationUtils;

    private final By cartIcon = YamlReader.getLocator("ProductsPage.cartIcon");

    @SuppressWarnings("rawtypes")
    public ProductsPage() {
        this.driver = Config.getDriver();
        this.applicationUtils = new ApplicationUtils((AppiumDriver) driver);
    }

    public void addProductToCart(String productName) {
        // Scroll to the product text first to ensure it's in view
        applicationUtils.scrollToText(productName);
        
        // Use a robust locator that finds the ADD TO CART button within the container of the specific product
        String xpathTemplate = YamlReader.getLocatorValue("ProductsPage.addToCartTemplate");
        String xpath = String.format(xpathTemplate, productName);
        applicationUtils.tapElement(By.xpath(xpath), 15L);
    }

    public CartPage goToCart() {
        applicationUtils.tapElement(cartIcon, 15L);
        return new CartPage();
    }
}