# SmartBank - JavaScript ES6/ES7 Assignment

## Problem Statement
Build a responsive client-side banking simulation using HTML5, CSS3 and JavaScript ES6/ES7 without a backend.

## Objectives
- Customer registration and validation
- Login and session management
- Dashboard and account management
- Deposit, withdrawal and fund transfer
- Transaction history with search, filters and sorting
- Loan EMI calculator
- Profile management
- LocalStorage and SessionStorage
- ES6 classes, modules, array methods, Promise, async/await and Fetch API
- Dynamic DOM rendering
- Light/Dark mode and mobile navigation

## Project Structure
```text
smartbank/
├── index.html
├── login.html
├── register.html
├── dashboard.html
├── accounts.html
├── transfer.html
├── transactions.html
├── loan.html
├── profile.html
├── css/
│   ├── style.css
│   └── dashboard.css
├── js/
│   ├── app.js
│   ├── auth.js
│   ├── customer.js
│   ├── account.js
│   ├── transaction.js
│   ├── transfer.js
│   ├── loan.js
│   ├── storage.js
│   └── api.js
└── data/
    └── customers.json
```

## Technologies
HTML5, CSS3, JavaScript ES6/ES7, LocalStorage, SessionStorage, JSON, Fetch API.

## Setup
Because the assignment uses ES6 modules and Fetch API, open the project through a local web server.

Example with VS Code:
1. Open the `smartbank` folder.
2. Install/use the Live Server extension.
3. Open `index.html` with Live Server.

## Demo Login
- Username: `demo`
- Password: `Demo@123`

A second account is available for transfer testing:
- Username: `test`
- Password: `Test@123`

## Pages
- `index.html`: home page and dynamically generated services
- `register.html`: registration and validation
- `login.html`: login and session handling
- `dashboard.html`: account summary and recent transactions
- `accounts.html`: account details, deposit and withdrawal
- `transfer.html`: fund transfer with confirmation
- `transactions.html`: history, search, filters, sorting and statistics
- `loan.html`: EMI calculator
- `profile.html`: editable customer profile

## JavaScript Concepts Demonstrated
let/const, arrow functions, template literals, destructuring, spread/rest, default parameters, map, filter, reduce, find, sort, classes, modules, Promise, async/await, Fetch API, JSON, DOM manipulation, events, form validation and exception handling.

## Important
This is only a fictional client-side simulation. Do not enter real banking passwords, account details or other sensitive information.
