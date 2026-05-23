package com.vachana.qa.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.vachana.qa.config.ConfigManager;

import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentReportManager {
    private static ExtentReports extentReports;

    private ExtentReportManager() {
    }

    public static synchronized ExtentReports getReporter() {
        if (extentReports == null) {
            Path reportDirectory = ConfigManager.getPath("report.dir", "target/reports").resolve("extent");
            try {
                Files.createDirectories(reportDirectory);
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to create Extent report directory", exception);
            }
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportDirectory.resolve("extent-report.html").toString());
            sparkReporter.config().setDocumentTitle("Automation Exercise QA Report");
            sparkReporter.config().setReportName("Hybrid UI + API Regression");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Application", ConfigManager.get("base.url"));
            extentReports.setSystemInfo("Browser", ConfigManager.get("browser", "chrome"));
            extentReports.setSystemInfo("Headless", ConfigManager.get("headless", "false"));
            extentReports.setSystemInfo("Environment", ConfigManager.get("env", ConfigManager.get("environment", "qa")));
        }
        return extentReports;
    }

    public static synchronized void flush() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
