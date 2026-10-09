# SmartBank — Banking Website (JavaScript ES6/ES7) by Srikanth Mareedu

## Problem Statement
SmartBank needs a client-side digital banking portal where customers can register, log in, manage accounts, deposit, withdraw, transfer funds, review transactions, calculate loans and edit their profile — with no backend. **Simulation only; no real banking data.**

## Objectives
Demonstrate ES6/ES7 classes, modules, array methods, destructuring, spread/rest, default parameters, template literals, Promises, async/await, Fetch API, localStorage/sessionStorage, form validation and dynamic DOM rendering.

## Technologies
HTML5, CSS3, JavaScript (ES modules). No libraries.

## Setup (important)
ES modules and `fetch()` do not work from `file://`. Run a local server from this folder:
- or VS Code → "Live Server" on `index.html`

Demo logins: `srikanth` / `Nani@1234`, `sirisha` / `Siri@1234`. To reset all data, clear the site's localStorage in DevTools.

## Pages
| Page | Purpose |
|---|---|
| index.html | Home, dynamic service cards, About, Contact |
| register.html | Registration with inline validation; creates account `SBxxxxxx` |
| login.html | Login, show/hide password, sessionStorage session |
| dashboard.html | Balance, total deposits/withdrawals, count, recent transactions |
| accounts.html | Account list, details, hide/show balances, deposit & withdrawal |
| transfer.html | IMPS/NEFT/RTGS transfer with limits and confirmation screen |
| transactions.html | Table, search/filters, sorting, reduce() statistics |
| loan.html | EMI calculator (Personal/Home/Car/Education) |
| profile.html | View and edit contact details with validation |

## Structure
`js/app.js` (page router) · `auth.js` · `customer.js` · `account.js` · `transaction.js` · `transfer.js` · `loan.js` · `storage.js` · `api.js` · plus `models.js` (Account/Customer classes) and `ui.js` (notifications, theme, nav). `data/customers.json` is fetched on first load and seeded into localStorage.

## JavaScript Concepts Demonstrated
| Concept | Where |
|---|---|
| let/const, arrow fns, template literals | everywhere |
| Destructuring, rest | `customer.js` registerCustomer, `account.js` saveAccounts(...accs) |
| Spread | `transaction.js` summarize/addTransaction |
| Default parameters | `models.js`, `transaction.js` query() |
| map / filter / reduce / sort / find | `transaction.js`, `account.js`, `auth.js` |
| Classes | `models.js` |
| Modules | all files (import/export) |
| Promises, async/await | `api.js` checkAccount/delay; used in deposit, withdraw, transfer, register |
| Fetch API | `api.js` — `.then()` version (home page) and async/await version (seeding) |
| Storage | localStorage (customers, accounts, transactions, beneficiaries, theme); sessionStorage (`loggedInUser`) |
| Exception handling | try/catch around all banking operations |
| E7 extras | TXN ID `TXN202610080001`, account no. `SB100001`, sorting, light/dark theme (persisted), mobile nav |

## Test Cases
| # | Test | Expected | Result |
|---|---|---|---|
| 1 | Register with invalid email/mobile/PIN/short password | Inline errors, no submit | Pass |
| 2 | Register valid customer, deposit ≥ ₹1000 | New account SB100003, redirect to login | Pass |
| 3 | Login with wrong password | Error toast | Pass |
| 4 | Open dashboard without login | Redirect to login.html | Pass |
| 5 | Deposit ₹1000 to SB100001 | Balance 86,000; Credit transaction | Pass |
| 6 | Withdraw more than balance | "Insufficient balance" warning | Pass |
| 7 | IMPS transfer ₹5000 to SB100002 | Confirmation screen, then both balances update | Pass |
| 8 | RTGS transfer ₹1000 | Blocked: below ₹2,00,000 minimum | Pass |
| 9 | Transfer to SB999999 | "Invalid beneficiary account" | Pass |
| 10 | EMI: ₹5,00,000 @ 10.5% for 5 yrs | ≈ ₹10,747 | Pass |

Note: passwords are stored in plain text in localStorage because this is a simulation. Never do this in a real application.
