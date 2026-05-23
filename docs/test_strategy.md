# Test Strategy

## Objective

Validate the highest-value customer and API journeys on `https://automationexercise.com` with a compact hybrid framework that a teammate can run, read, and extend. The suite focuses on e-commerce risk areas: account/session behavior, product discovery, cart/checkout integrity, form validation, and API contract reliability.

## Scope

In scope:

- UI smoke flows: registration, login, logout, product search, product detail, add/remove cart, quantity, checkout, contact form, subscription, and category navigation.
- UI negative and edge flows: invalid login, duplicate signup, missing/empty data behavior, quantity transfer to cart, ad/popup click interception.
- API tests: product list, brand list, search, unsupported methods, missing required parameters, account create/update/delete.
- Reporting, screenshots, logging, retry, tag execution, parallel-ready runner, and GitHub Actions.

Out of scope for this weekend submission:

- Full visual regression and accessibility automation.
- Payment gateway security testing, because the practice site accepts fake data and has no real payment integration.
- Exhaustive browser matrix. The design supports Chrome, Firefox, and Edge, but CI runs Chrome headless for speed and reliability.
- Load/performance testing beyond response-time guardrails on critical APIs.

## Top Risk Areas

1. Account and session flows: signup/login/logout/delete are stateful and can pollute public shared test data if not isolated. The framework creates unique throwaway users and cleans them up.
2. Cart and checkout integrity: wrong quantity, stale cart state, or broken checkout navigation directly affects purchase confidence.
3. Third-party ads and overlays: the site includes dynamic ads that can intercept clicks. The framework centralizes safe click behavior and ad neutralization in utilities.

## Tool Choices

- Java: familiar enterprise SDET language with strong Selenium and REST Assured support.
- Selenium WebDriver: direct browser automation with cross-browser support through Selenium Manager.
- Cucumber BDD: business-readable scenarios and tag-based execution.
- TestNG: parallel execution, retry analyzer, and flexible runner integration.
- REST Assured: concise API validation with schema, response code, and response time assertions.
- Maven: dependency management, repeatable local execution, and CI-friendly profiles.
- Extent Reports plus Cucumber HTML: readable execution summary plus step-level BDD output.
- GitHub Actions: simple push/PR execution with downloadable reports.

## What I Would Add Next

- Dockerized browser execution with Selenium Grid or Selenoid.
- Accessibility checks with axe-core for checkout, auth, and forms.
- Visual snapshots for product cards and checkout confirmation.
- Contract tests against versioned OpenAPI specs if the API exposed one.
- Quarantine process for known third-party ad flakiness.
- Synthetic monitoring for site availability before the suite starts.
