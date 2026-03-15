package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import page.CartPage;
import page.CheckoutPage;
import page.LoginPage;
import page.ProductsPage;

import io.cucumber.datatable.DataTable;
import org.testng.Assert;
import java.util.List;
import java.util.Map;

public class PurchaseStep {
    LoginPage lp = new LoginPage();
    ProductsPage productsPage = new ProductsPage();
    CartPage cartPage = new CartPage();
    CheckoutPage checkoutPage = new CheckoutPage();

    @Given("I am on the login screen")
    public void i_am_on_the_login_screen() {
        Assert.assertTrue(lp.isLoginScreenDisplayed(), "Login screen is not displayed!");
    }

    @And("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String username, String password) {
         lp.login(username,password);
    }

    @When("I add {string} to cart")
    public void iAddToCart(String productName) {
        productsPage.addProductToCart(productName);
    }

    @And("I go to cart")
    public void iGoToCart() {
        cartPage = productsPage.goToCart();
    }

    @And("I proceed to checkout")
    public void iProceedToCheckout() {
        checkoutPage = cartPage.proceedToCheckout();
    }

    @And("I enter checkout information:")
    public void iEnterCheckoutInformation(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        String firstName = data.get(0).get("firstName");
        String lastName = data.get(0).get("LastName");
        String zipCode = data.get(0).get("ZipCode");
        checkoutPage.enterCheckoutInfo(firstName, lastName, zipCode);
    }

    @And("I complete the checkout")
    public void iCompleteTheCheckout() {
        checkoutPage.clickFinish();
    }

    @Then("I should see the order completion message")
    public void iShouldSeeTheOrderCompletionMessage() {
        Assert.assertTrue(checkoutPage.isOrderComplete(), "Order completion message not displayed!");
    }
}
