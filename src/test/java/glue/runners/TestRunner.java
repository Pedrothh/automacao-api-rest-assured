package glue.runners;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "glue",
        plugin = {
                "pretty",
                "html:target/cucumber-reports",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        tags = ""
)
public class TestRunner extends AbstractTestNGCucumberTests  {
}
