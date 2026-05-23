# Bug Reports

## BUG-001: API returns HTTP 200 for unsupported or invalid requests

- Severity: Medium
- Area: API contract
- Environment: automationexercise.com, observed May 23, 2026
- Steps:
  1. Send `POST https://automationexercise.com/api/productsList`.
  2. Send `POST https://automationexercise.com/api/searchProduct` without `search_product`.
  3. Send `DELETE https://automationexercise.com/api/verifyLogin`.
- Expected:
  - Unsupported methods should return HTTP 405.
  - Missing required parameters should return HTTP 400.
- Actual:
  - All calls return HTTP 200, while the JSON body contains `responseCode` values like 405 or 400.
- Impact:
  - API clients, monitors, and gateways that rely on HTTP status cannot correctly detect failures.
- Evidence:
  - `POST /api/productsList` returned HTTP 200 with `{"responseCode":405,"message":"This request method is not supported."}`.

## BUG-002: Recommended item displays price as product name

- Severity: Low
- Area: Home page product content
- Environment: automationexercise.com, observed May 23, 2026
- Steps:
  1. Open the home page.
  2. Scroll to Recommended Items.
  3. Review the product card for product id 3.
- Expected:
  - Product name should be displayed, for example `Sleeveless Dress`.
- Actual:
  - Product name line displays `Rs. 1000`, duplicating the price instead of showing the name.
- Impact:
  - Product content is misleading and weakens trust in catalog quality.

## BUG-003: Contact form shows success even when key fields are blank

- Severity: Medium
- Area: Contact Us validation
- Environment: automationexercise.com, observed May 23, 2026
- Steps:
  1. Open `/contact_us`.
  2. Enter only a syntactically valid email address.
  3. Leave name, subject, and message blank.
  4. Click Submit and accept the confirmation dialog.
- Expected:
  - Required validation should block submission for name, subject, and message.
- Actual:
  - The page shows `Success! Your details have been submitted successfully.`
- Impact:
  - Empty feedback can be submitted, causing poor data quality and false success signals.
