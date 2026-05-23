# Automation Exercise Hybrid QA Framework

Enterprise-style QA automation framework for [Automation Exercise](https://automationexercise.com) using Java, Selenium WebDriver, TestNG, Cucumber BDD, Maven, and REST Assured.

This repository contains the requested framework code, API/UI scenarios, CI pipeline, test strategy, test cases, and bug reports.

## Tech Stack

- Java 17
- Maven
- Selenium WebDriver 4
- TestNG
- Cucumber BDD
- REST Assured
- Extent Reports
- Cucumber HTML/JSON/JUnit reports
- Log4j2
- GitHub Actions

## Project Structure

```text
.
|-- .github/workflows/qa-automation.yml
|-- docs/
|   |-- bug_reports.md
|   |-- test_cases.md
|   `-- test_strategy.md
|-- pom.xml
|-- testng.xml
|-- src/test/java/com/vachana/qa/
|   |-- api/             REST Assured clients, endpoints, payloads, assertions
|   |-- config/          Config loader with env and system-property overrides
|   |-- constants/       Framework constants
|   |-- context/         Thread-local scenario state
|   |-- driver/          Driver factory and browser selection
|   |-- hooks/           Cucumber setup, cleanup, screenshots, reports
|   |-- listeners/       TestNG retry analyzer and annotation transformer
|   |-- models/          Test data models
|   |-- pages/           Page Object Model classes
|   |-- reports/         Extent report lifecycle
|   |-- runners/         Cucumber TestNG runner
|   |-- steps/           UI and API step definitions
|   `-- utils/           Waits, elements, screenshots, ads, logging, test data
`-- src/test/resources/
    |-- config/          Runtime properties
    |-- features/        Cucumber UI and API feature files
    |-- schemas/         JSON schema validations
    `-- testdata/        Externalized users, payment, upload data
```

## Setup

Install:

- JDK 17
- Maven 3.9+
- Chrome browser
- IntelliJ IDEA, optional but recommended

Verify:

```bash
java -version
mvn -version
```

The framework uses Selenium Manager, so no manual ChromeDriver download is needed for standard local runs.

## Configuration

Default config lives in:

```text
src/test/resources/config/config.properties
```

Common overrides:

```bash
-Dbrowser=chrome
-Dheadless=false
-Denv=qa
-Dparallel.threads=2
-Dcucumber.filter.tags="@ui and @smoke"
```

Headed mode:

```bash
mvn clean test -Pui-smoke -Dheadless=false
```

Headless mode:

```bash
mvn clean test -Pregression,headless -Dheadless=true
```

API only:

```bash
mvn clean test -Papi
```

Run a tag:

```bash
mvn clean test -Dcucumber.filter.tags="@api or (@ui and @smoke)"
```

## IntelliJ Execution

1. Open the repo as a Maven project.
2. Let IntelliJ import dependencies from `pom.xml`.
3. Open `src/test/java/com/vachana/qa/runners/TestRunner.java`.
4. Create a TestNG run configuration for `TestRunner`.
5. Add VM options:

```text
-Dheadless=false -Dbrowser=chrome -Dcucumber.filter.tags="@ui and @smoke"
```

6. Run the configuration. Chrome launches automatically and opens `https://automationexercise.com`.

To switch to headless in IntelliJ, change:

```text
-Dheadless=true
```

## Reports and Logs

After execution:

```text
target/cucumber-reports/cucumber.html
target/cucumber-reports/cucumber.json
target/reports/extent/extent-report.html
target/screenshots/
target/logs/framework.log
```

Screenshots are attached to Cucumber on failed UI steps and saved under `target/screenshots`. Extent reports receive scenario status and failure screenshots.

## Execution Flow

1. `TestRunner` discovers feature files and step definitions.
2. `Hooks` starts the Extent scenario entry.
3. UI scenarios start a browser and navigate to the base URL.
4. Scenarios tagged `@requiresRegisteredUser` create a throwaway user through the API before UI steps begin.
5. Page objects perform interactions through reusable safe-click, wait, and ad-handling utilities.
6. API scenarios use REST Assured clients and schema assertions.
7. Failed UI steps attach screenshots.
8. Scenarios tagged `@cleanupUser` delete created accounts through the API.
9. Cucumber and Extent reports are flushed at the end.

## Key File Breakdown

| File | Purpose |
|---|---|
| `pom.xml` | Maven dependencies, Surefire runner, profiles, tag and headless overrides. |
| `testng.xml` | Optional IntelliJ/TestNG suite entrypoint with retry listener. |
| `config/config.properties` | Browser, URL, wait, retry, report, and data defaults. |
| `driver/DriverFactory.java` | Thread-safe browser creation for Chrome, Firefox, and Edge. |
| `hooks/Hooks.java` | Scenario lifecycle, API prerequisite users, cleanup, screenshots, Extent status. |
| `pages/*.java` | Page Object Model for home, auth, account, product, cart, checkout, contact, and category pages. |
| `utils/ElementUtils.java` | Safe click, typing, scrolling, select, alert handling, and ad-aware interaction. |
| `utils/AdHandler.java` | Centralized handling for ads, overlays, hidden iframes, and intercepted clicks. |
| `api/ApiClient.java` | REST Assured request specification and HTTP method helpers. |
| `api/ApiAssertions.java` | HTTP status, body response code, schema, message, and response-time assertions. |
| `features/ui/*.feature` | Business-readable UI BDD scenarios. |
| `features/api/*.feature` | API positive, negative, schema, and CRUD scenarios. |
| `.github/workflows/qa-automation.yml` | Push/PR CI pipeline with report artifact publishing. |

## Design Decisions

- Page Object Model keeps selectors and page behavior out of step definitions.
- Cucumber scenarios stay business-readable while Java steps keep implementation reusable.
- API-created prerequisite users avoid depending on permanent shared test accounts.
- System properties override config files so CI, IntelliJ, and Maven can run the same code differently.
- Ads and dynamic overlays are handled in `AdHandler` and `ElementUtils`, not scattered in tests.
- TestNG retry is available but conservative with `retry.count=1` to avoid hiding real defects.
- The framework supports Chrome, Firefox, and Edge by design; CI uses Chrome headless for speed.

## Assumptions

- Only fake data is used against the public site.
- The public practice site may be unavailable or slower than a production app.
- Automation Exercise API returns HTTP 200 for some business errors; tests assert both HTTP status and body `responseCode`.
- CI publishes reports as GitHub Actions artifacts instead of deploying static pages.

## CI/CD

Workflow:

```text
.github/workflows/qa-automation.yml
```

Triggers:

- Push to `main`, `master`, or `develop`
- Pull request into those branches
- Manual `workflow_dispatch`

Pipeline steps:

1. Checkout repository.
2. Install Java 17.
3. Install Chrome.
4. Run Maven regression in headless Chrome.
5. Upload Cucumber report, Extent report, screenshots, and logs as artifacts.

No secrets are required for the current public target. If future tests require credentials or tokens, store them in GitHub repository secrets and pass them as environment variables or Maven system properties.

## Assignment Documents

- Test strategy: `docs/test_strategy.md`
- Test cases: `docs/test_cases.md`
- Bug reports: `docs/bug_reports.md`

## AI Usage

AI assistance was used to accelerate framework scaffolding, documentation structure, and code review thinking. The live application and API behavior were still checked directly against Automation Exercise pages and endpoints before finalizing selectors, endpoints, and bug reports.

## Challenges

- The public site includes ads and overlays that can intercept clicks.
- API business errors are represented in JSON `responseCode` while HTTP status often remains 200.
- Public shared environments make permanent test accounts risky, so this suite generates unique data and cleans up.
- Local verification in this workspace was limited because Java and Maven were not installed on PATH.

## Future Improvements

- Add Docker and Selenium Grid for reproducible browser execution.
- Add axe accessibility checks.
- Publish reports to GitHub Pages.
- Add visual regression snapshots for product and checkout pages.
- Add contract tests from an OpenAPI definition if one becomes available.
- Add Jenkins pipeline parity for non-GitHub shops.
- Add database validation only if the application exposes a safe test database or seeded environment.

## Interview Questions

1. How does Selenium WebDriver differ from JavaScript executor based interaction?
2. How would you debug `ElementClickInterceptedException` on an ad-heavy site?
3. Why should implicit waits be avoided in a modern framework?
4. How does Cucumber scenario isolation work with TestNG parallel execution?
5. What belongs in page objects versus step definitions?
6. How would you design test data for public shared environments?
7. How do you decide whether a flaky test should be retried or fixed?
8. Why assert both HTTP status and business `responseCode`?
9. How would you validate JSON schema and still keep tests resilient?
10. What are the risks of running UI tests in parallel against one public environment?
11. How would you add cross-browser support without duplicating tests?
12. What should be logged in test automation, and what should be masked?
13. How do Cucumber tags help CI pipeline design?
14. What should a framework do when a prerequisite account creation API fails?
15. How would you scale this framework for multiple teams?

## Live Coding and Change Requests

| Request | Files likely to change | Implementation approach |
|---|---|---|
| Add Safari or remote browser support | `driver/DriverFactory.java`, `config.properties` | Add browser enum, options/capabilities, and remote URL handling. |
| Add stronger retry rules | `listeners/RetryAnalyzer.java`, `config.properties` | Retry only known transient exceptions and log retry reason. |
| Add parallel execution tuning | `pom.xml`, `testng.xml`, `TestRunner.java` | Adjust data provider thread count and ensure all shared state remains thread-local. |
| Add Docker execution | `Dockerfile`, `docker-compose.yml`, `.github/workflows/*` | Run Maven tests against Selenium standalone Chrome or Grid. |
| Add Jenkins | `Jenkinsfile` | Reuse Maven commands, archive `target` reports, publish HTML. |
| Add API token handling | `ApiClient.java`, `config.properties`, CI secrets | Read token from env/secret and inject auth header through request spec. |
| Add environment switching | `config/*.properties`, `ConfigManager.java` | Add `dev`, `qa`, `stage` property files and select with `-Denv=stage`. |
| Add data-driven UI tests | `testdata/*.json`, step definitions, feature outlines | Keep examples business-readable and large data sets externalized. |
| Add cloud execution | `DriverFactory.java`, CI secrets | Add BrowserStack/Sauce capabilities and credentials through secrets. |
| Add database validation | new `db/` package, config, test data | Keep DB checks read-only and behind environment-specific config. |

## Useful Commands

```bash
# Full regression, stable sequential runner
mvn clean test -Pregression

# Chrome headed smoke
mvn clean test -Pui-smoke -Dheadless=false

# API only
mvn clean test -Papi

# Specific tag
mvn clean test -Dcucumber.filter.tags="@checkout"

# Override browser
mvn clean test -Dbrowser=edge -Dheadless=false

# Optional parallel runner for framework demonstration
mvn clean test -Dtest=ParallelTestRunner -Dcucumber.filter.tags="@api"
```
