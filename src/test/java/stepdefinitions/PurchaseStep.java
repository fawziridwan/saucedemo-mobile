package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import page.CartPage;
import page.CheckoutPage;
import page.LoginPage;
import page.ProductsPage;

public class PurchaseStep {
    LoginPage lp = new LoginPage();
    ProductsPage productsPage = new ProductsPage();
    CartPage cartPage = new CartPage();
    CheckoutPage checkoutPage = new CheckoutPage();

    @Given("I am on the login screen")
    public void i_am_on_the_login_screen() {
        lp.isLoginScreenDisplayed();
    }

    @And("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String username, String password) {
         lp.login(username,password);
    }

    @When("I add {string} to cart")
    public void iAddToCart(String productName) {
        if ("Sauce Labs Backpack".equals(productName)) {
            productsPage.addBackpack();
        } else if ("Sauce Labs Bike Light".equals(productName)) {
            productsPage.addBikeLight();
        } else {
            throw new IllegalArgumentException("Unsupported product: " + productName);
        }
    }

    @And("I go to cart")
    public void iGoToCart() {
        cartPage = productsPage.goToCart();
    }

    @And("I proceed to checkout")
    public void iProceedToCheckout() {
        checkoutPage = cartPage.proceedToCheckout();
    }

    @Then("I should see the Homepage")
    public void iShouldSeeTheHomepage() {
        lp.isProductsTitleDisplayed();
        lp.isSwagLabsLogoDisplayed();
    }
}
