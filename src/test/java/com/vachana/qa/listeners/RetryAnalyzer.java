package com.vachana.qa.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int attempt;

    @Override
    public boolean retry(ITestResult result) {
        int retryLimit = Integer.parseInt(System.getProperty("retry.count", "1"));

        if (attempt < retryLimit) {
            attempt++;
            return true;
        }

        return false;
    }
}