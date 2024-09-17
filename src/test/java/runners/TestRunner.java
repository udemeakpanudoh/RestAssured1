package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags="@apiWeatherTest",
        features = "src/test/resources/Features",
        glue = "StepDefinitions",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true,
        publish = true
)
public class TestRunner {
}
