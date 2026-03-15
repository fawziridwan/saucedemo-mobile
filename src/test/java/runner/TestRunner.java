package runner; // Note: changed from steps.runners to runners

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/resources/features"}
        ,glue = {""}
        ,plugin = {"pretty", "html:target/report/report.html","json:target/cucumber.json"}
        ,tags = "@purchase"
)

public class TestRunner {

}