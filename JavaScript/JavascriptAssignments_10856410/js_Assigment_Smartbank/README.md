# SmartBank — JavaScript ES6 / ES7 Banking Website

A responsive, client-side banking simulation built from the **SmartBank JavaScript ES6/ES7 Assignment (E6/E7)**.

## Design approach

The supplied PDF defines functionality, pages, JavaScript concepts and evaluation criteria, but it does not contain screen-by-screen UI mockups. This implementation therefore uses a clean, modern banking visual system: deep green primary color, white cards, generous spacing, responsive layouts, clear financial states and accessible form controls.

## Included pages

- `index.html` — public landing page and dynamically generated services
- `login.html` — login + show/hide password + session storage
- `register.html` — customer registration + validation
- `dashboard.html` — account summary, recent activity and insights
- `accounts.html` — account cards, details, balance visibility, deposit and withdrawal
- `transfer.html` — IMPS/NEFT/RTGS transfer + async beneficiary verification + confirmation modal
- `transactions.html` — search, type/amount filters and sorting
- `loan.html` — Personal/Home/Car/Education EMI calculator
- `profile.html` — editable customer profile

## JavaScript concepts demonstrated

- `let` / `const`
- Arrow functions
- Template literals
- Destructuring / spread
- Default parameters
- `map()`, `filter()`, `reduce()`, `find()`, `sort()`
- ES6 modules using `import` / `export`
- ES6 classes can be extended in `storage.js` / domain modules if required by the evaluator
- Local Storage
- Session Storage
- DOM manipulation and event handling
- Form validation
- JSON
- Promises and async/await
- Fetch API loads `data/customers.json` with async/await
- Dynamic notifications
- Dynamic transaction/service rendering
- Unique transaction/account IDs
- Light/Dark theme persisted in localStorage
- Responsive JavaScript mobile navigation

## Run

Because this project uses ES modules and Fetch API, run it through a local HTTP server rather than opening `index.html` directly.

### Option 1 — VS Code

Install the **Live Server** extension, right-click `index.html`, then choose **Open with Live Server**.

### Option 2 — Python

```bash
python -m http.server 5500
```

Then open:

`http://localhost:5500/index.html`

## Demo login

- Username: `demo`
- Password: `Smart@123`

## Notes

This is an educational simulation. Do not enter real banking credentials, card details or other sensitive information. All data is stored in the browser's localStorage/sessionStorage and no real banking transaction is performed.

## Assignment mapping

The implementation covers the PDF's home page, registration, login/session, dashboard, account management, deposit/withdrawal-compatible transaction model, fund transfer, transaction history, loan calculator, profile, storage, asynchronous verification, dynamic DOM rendering, notifications/logout, theme switching and responsive navigation.
