package com.vachana.qa.runners;

import com.vachana.qa.listeners.AnnotationTransformer;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@Listeners(AnnotationTransformer.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"com.vachana.qa.steps", "com.vachana.qa.hooks"},
        plugin = {
                "pretty",
                "com.vachana.qa.reports.ExtentCucumberStepLogger",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml",
                "rerun:target/cucumber-reports/rerun.txt"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
