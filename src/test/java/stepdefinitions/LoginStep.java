package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import page.LoginPage;

public class LoginStep {
    LoginPage lp = new LoginPage();

    @Given("I am on login screen")
    public void i_am_on_login_screen() {
        Assert.assertTrue(lp.isLoginScreenDisplayed(), "Login screen is not displayed");
    }

    @Then("I should see the {string}")
    public void iShouldSeeThe(String message) {
        lp.validateMessageError(message);
    }

    @And("I logged in with {string} and {string}")
    public void iLoggedInWithAnd(String username, String password) {
        lp.login(username,password);
    }


    @Then("I should see the Homepage")
    public void iShouldSeeTheHomepage() {
        lp.isProductsTitleDisplayed();
        lp.isSwagLabsLogoDisplayed();
    }
}
