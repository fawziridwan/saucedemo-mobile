package runner; // Note: changed from steps.runners to runners

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/resources/features"}
        ,glue = {"stepdefinitions", "utils"}
        ,plugin = {"pretty", "html:target/report/report.html","json:target/cucumber.json", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
        ,tags = "@purchase"
)

public class TestRunner {

}