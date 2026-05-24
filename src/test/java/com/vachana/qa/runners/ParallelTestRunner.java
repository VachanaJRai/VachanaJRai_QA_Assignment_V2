package com.vachana.qa.runners;

import com.vachana.qa.listeners.AnnotationTransformer;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@Listeners(AnnotationTransformer.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.vachana.qa.steps", "com.vachana.qa.hooks"},
        plugin = {
                "pretty",
                "com.vachana.qa.reports.ExtentCucumberStepLogger",
                "html:target/cucumber-reports/cucumber-parallel.html",
                "json:target/cucumber-reports/cucumber-parallel.json",
                "junit:target/cucumber-reports/cucumber-parallel.xml",
                "rerun:target/cucumber-reports/rerun-parallel.txt"
        },
        monochrome = true
)
public class ParallelTestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
