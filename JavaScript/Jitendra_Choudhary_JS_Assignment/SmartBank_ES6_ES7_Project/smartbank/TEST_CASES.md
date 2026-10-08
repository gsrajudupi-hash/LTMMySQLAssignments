# Sample Test Cases and Results

| ID | Scenario | Expected result | Status |
|---|---|---|---|
| TC01 | Login using demo / Demo@123 | Dashboard opens | Pass |
| TC02 | Register with mismatched passwords | Validation message shown | Pass |
| TC03 | Deposit positive amount | Balance and transaction update | Pass |
| TC04 | Withdraw above balance | Insufficient balance message | Pass |
| TC05 | Transfer valid amount | Confirmation then success | Pass |
| TC06 | Search and sort transactions | Filtered/sorted rows render | Pass |
| TC07 | Calculate EMI | EMI, interest and payable render | Pass |
| TC08 | Toggle dark mode | Theme persists after refresh | Pass |
| TC09 | Open protected page logged out | Redirect to login | Pass |
| TC10 | Update profile | Changes persist in localStorage | Pass |
