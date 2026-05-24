package com.vachana.qa.reports;

import com.aventstack.extentreports.ExtentTest;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;

public class ExtentCucumberStepLogger implements ConcurrentEventListener {
    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::handleStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleStepFinished);
    }

    private void handleStepStarted(TestStepStarted event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep pickleStep)) {
            return;
        }

        String keyword = pickleStep.getStep().getKeyword();
        String text = pickleStep.getStep().getText();
        StepLogContext.start(keyword, text);

        ExtentTest test = ExtentTestManager.getTest();
        if (test != null) {
            test.info("START | " + StepLogContext.currentStepDisplayName(0));
        }
    }

    private void handleStepFinished(TestStepFinished event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep)) {
            return;
        }

        Status status = event.getResult().getStatus();
        Throwable error = event.getResult().getError();
        StepLogContext.finish(status.name(), error);

        ExtentTest test = ExtentTestManager.getTest();
        if (test == null) {
            return;
        }

        String stepName = StepLogContext.currentStepDisplayName(0);
        switch (status) {
            case PASSED -> test.pass(statusLabel("PASS", stepName));
            case SKIPPED -> test.skip(statusLabel("SKIP", stepName));
            case FAILED -> {
                test.fail(statusLabel("FAIL", stepName));
                if (error != null) {
                    test.fail(error);
                }
            }
            default -> test.warning(statusLabel(status.name(), stepName));
        }
    }

    private String statusLabel(String status, String stepName) {
        String prefix = StepLogContext.currentStepIsValidation() ? "VALIDATION" : "STEP";
        return prefix + " " + status + " | " + stepName;
    }
}
