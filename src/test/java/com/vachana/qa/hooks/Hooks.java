package com.vachana.qa.hooks;

import com.aventstack.extentreports.Status;
import com.vachana.qa.api.ApiAssertions;
import com.vachana.qa.api.ApiClient;
import com.vachana.qa.api.ApiEndpoints;
import com.vachana.qa.api.ApiPayloadFactory;
import com.vachana.qa.config.ConfigManager;
import com.vachana.qa.context.TestContext;
import com.vachana.qa.driver.DriverFactory;
import com.vachana.qa.models.Customer;
import com.vachana.qa.reports.ExtentReportManager;
import com.vachana.qa.reports.ExtentTestManager;
import com.vachana.qa.utils.LoggerUtils;
import com.vachana.qa.utils.RandomDataUtils;
import com.vachana.qa.utils.ScreenshotUtils;
import io.cucumber.java.*;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class Hooks {
    private static final Logger LOGGER = LoggerUtils.getLogger(Hooks.class);

    @BeforeAll
    public static void beforeAll() {
        ExtentReportManager.getReporter();
    }

    @Before(order = 0)
    public void startScenario(Scenario scenario) {
        ExtentTestManager.startTest(scenario.getName());
        ExtentTestManager.getTest().assignCategory(String.join(",", scenario.getSourceTagNames()));
        TestContext.put("stepCounter", 0);
        LOGGER.info("Starting scenario: {}", scenario.getName());
    }

    @Before(value = "@requiresRegisteredUser", order = 1)
    public void createRegisteredUser() {
        Customer customer = RandomDataUtils.uniqueCustomer();
        var response = new ApiClient().postForm(ApiEndpoints.CREATE_ACCOUNT, ApiPayloadFactory.createAccountPayload(customer));
        ApiAssertions.assertHttpStatus(response, 200);
        ApiAssertions.assertBusinessCode(response, 201);
        TestContext.put("customer", customer);
        LOGGER.info("Created prerequisite API user: {}", customer.email());
    }

    @Before(value = "not @api", order = 10)
    public void startBrowser() {
        DriverFactory.initializeDriver().get(ConfigManager.get("base.url"));
    }

    @AfterStep(value = "not @api")
    public void captureStepScreenshot(Scenario scenario) {
        if (!DriverFactory.hasDriver()) {
            return;
        }

        int stepNumber = TestContext.find("stepCounter", Integer.class).orElse(0) + 1;
        TestContext.put("stepCounter", stepNumber);

        Path screenshot = ScreenshotUtils.saveStepScreenshot(scenario.getName(), stepNumber);
        LOGGER.info("Saved step screenshot: {}", screenshot.toAbsolutePath());

        try {
            ExtentTestManager.getTest()
                    .info(String.format("Step %03d screenshot", stepNumber))
                    .addScreenCaptureFromPath(screenshot.toAbsolutePath().toString());
        } catch (Exception exception) {
            LOGGER.warn("Unable to attach step screenshot to Extent report: {}", exception.getMessage());
        }

        if (scenario.isFailed()) {
            scenario.attach(ScreenshotUtils.screenshotBytes(), "image/png", String.format("failed-step-%03d", stepNumber));
        }
    }

    @After(value = "not @api", order = 100)
    public void finishUiScenario(Scenario scenario) {
        if (DriverFactory.hasDriver()) {
            if (scenario.isFailed()) {
                Path screenshot = ScreenshotUtils.saveScenarioScreenshot(scenario.getName(), "failure_final");
                scenario.attach(ScreenshotUtils.screenshotBytes(), "image/png", "failure-screenshot");
                ExtentTestManager.getTest().log(Status.FAIL, "Scenario failed");
                try {
                    ExtentTestManager.getTest().addScreenCaptureFromPath(screenshot.toAbsolutePath().toString());
                } catch (Exception exception) {
                    LOGGER.warn("Unable to attach failure screenshot to Extent report: {}", exception.getMessage());
                }
            } else {
                ExtentTestManager.getTest().pass("Scenario passed");
            }
            DriverFactory.quitDriver();
        }
    }

    @After(value = "@api", order = 100)
    public void finishApiScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            ExtentTestManager.getTest().fail("API scenario failed");
        } else {
            ExtentTestManager.getTest().pass("API scenario passed");
        }
    }

    @After(value = "@cleanupUser", order = 10)
    public void cleanupUser() {
        TestContext.find("customer", Customer.class).ifPresent(customer -> {
            var response = new ApiClient().deleteForm(ApiEndpoints.DELETE_ACCOUNT, ApiPayloadFactory.deletePayload(customer));
            LOGGER.info("Cleanup user {} returned body responseCode={}", customer.email(), response.jsonPath().getInt("responseCode"));
        });
    }

    @After(order = 0)
    public void clearScenarioContext(Scenario scenario) {
        LOGGER.info("Finished scenario: {} status={}", scenario.getName(), scenario.getStatus());
        ExtentTestManager.removeTest();
        TestContext.clear();
    }

    @AfterAll
    public static void afterAll() {
        ExtentReportManager.flush();
    }
}