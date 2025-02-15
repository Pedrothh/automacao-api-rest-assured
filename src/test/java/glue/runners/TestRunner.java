package glue.runners;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "glue",
        plugin = {
                "pretty",
                "json:target/cucumber-report/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        tags = ""
)
public class TestRunner extends AbstractTestNGCucumberTests  {
}
