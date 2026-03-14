package page;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Config;

public class ProductsPage {
    WebDriverWait wait;
    private WebDriver driver;

    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc='test-ADD TO CART'])[1]")
    private WebElement addBackpackToCart;

    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc='test-ADD TO CART'])[2]")
    private WebElement addBikeLightToCart;

    @AndroidFindBy(accessibility = "test-Cart")
    private WebElement cartIcon;

    public ProductsPage() {
        this.driver = Config.getDriver();
        wait = new WebDriverWait(driver, 15);
    }

    public void addBackpack() {
        wait.until(ExpectedConditions.elementToBeClickable(addBackpackToCart)).click();
    }

    public void addBikeLight() {
        wait.until(ExpectedConditions.elementToBeClickable(addBikeLightToCart)).click();
    }

    public CartPage goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
        return new CartPage();
    }
}