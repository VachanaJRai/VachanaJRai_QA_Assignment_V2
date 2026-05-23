# Test Cases

| ID | Title | Type | Priority | Steps | Expected Result |
|---|---|---|---|---|---|
| TC-001 | Register a new user with valid data | Positive | P0 | Open home, go to Signup/Login, enter unique name/email, complete account details, create account. | Account created message appears and user is logged in. |
| TC-002 | Login with valid registered user | Positive | P0 | Create user via API, open login, enter email/password, submit. | Header shows logged-in user name. |
| TC-003 | Login with invalid password | Negative | P0 | Open login, enter non-existing email or wrong password, submit. | Error message says email or password is incorrect. |
| TC-004 | Logout from active session | Positive | P1 | Login as registered user, click Logout. | User returns to login page and authenticated nav items disappear. |
| TC-005 | Signup with existing email | Negative | P1 | Create user via API, try signup with same email. | Existing email validation is displayed. |
| TC-006 | Search products by keyword | Positive | P0 | Open Products, search for `top`. | Searched Products heading appears and returned product names match the keyword. |
| TC-007 | Search with special characters | Edge | P2 | Open Products, search for `%$#`. | Page should not crash; no unrelated products should be shown. |
| TC-008 | Product detail contains merchandising fields | Positive | P1 | Open Products, click first View Product. | Name, category, price, availability, condition, and brand are visible. |
| TC-009 | Add two products to cart | Positive | P0 | Add product 1, continue shopping, add product 2, open cart. | Cart contains two line items with price, quantity, and total. |
| TC-010 | Quantity from detail page persists to cart | Edge | P0 | Open product detail, set quantity to 4, add to cart, open cart. | Cart quantity is exactly 4. |
| TC-011 | Remove product from cart | Positive | P1 | Add one product, open cart, click remove. | Cart becomes empty or item count decreases. |
| TC-012 | Checkout as logged-in user | Positive | P0 | Login, add product, checkout, enter comment, pay with fake card. | Order placed confirmation appears. |
| TC-013 | Contact form accepts upload | Positive | P1 | Open Contact Us, fill fields, upload small text file, submit and accept confirmation. | Success message appears. |
| TC-014 | Contact form with blank name and message | Non-obvious Negative | P2 | Open Contact Us, fill only valid email, submit, accept confirmation. | Required validation should block submission. |
| TC-015 | API unsupported method uses proper HTTP status | Non-obvious API | P1 | POST `/api/productsList`, PUT `/api/brandsList`, DELETE `/api/verifyLogin`. | HTTP status should match 405, not only body `responseCode`. |
