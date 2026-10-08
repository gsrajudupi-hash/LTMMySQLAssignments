import {
  getLoggedInUser, currentCustomer, getAccounts, saveAccounts,
  getTransactions, showMessage, money, logout, getCustomers, saveCustomers
} from "./storage.js";
import { protectPage, setupLogin, setupRegistration } from "./auth.js";
import { Account } from "./account.js";
import { createTransaction, updateTransactionBalance, getAccountTransactions, transactionStats } from "./transaction.js";
import { setupTransfer } from "./transfer.js";
import { setupLoanCalculator } from "./loan.js";
import { loadSampleData } from "./api.js";

const services = [
  ["Savings Account", "Simple everyday savings."],
  ["Current Account", "Business-ready account services."],
  ["Fixed Deposit", "Plan your savings with fixed deposits."],
  ["Personal Loan", "Flexible personal finance."],
  ["Home Loan", "Support for your home goals."],
  ["Education Loan", "Finance higher education."],
  ["Credit Card", "Convenient digital payments."],
  ["Internet Banking", "Manage banking online."]
];

const protectedPages = [
  "dashboard.html", "accounts.html", "transfer.html",
  "transactions.html", "profile.html"
];

const pageName = location.pathname.split("/").pop() || "index.html";

const seedData = async () => {
  if (getCustomers().length) return;
  const sample = await loadSampleData();
  if (!sample.length) return;

  saveCustomers(sample.customers || []);
  saveAccounts(sample.accounts || []);
  localStorage.setItem("transactions", JSON.stringify(sample.transactions || []));
};

const renderHeader = () => {
  const header = document.getElementById("site-header");
  if (!header) return;

  const loggedIn = Boolean(getLoggedInUser());
  header.innerHTML = `
    <nav class="nav">
      <a class="brand" href="index.html">SmartBank</a>
      <button class="menu-btn" id="menu-btn">☰</button>
      <div class="nav-links" id="nav-links">
        <a href="index.html">Home</a>
        <a href="index.html#about">About</a>
        <a href="loan.html">Loans</a>
        ${loggedIn ? `<a href="dashboard.html">Dashboard</a><a href="accounts.html">Accounts</a><a href="transfer.html">Transfer</a><a href="transactions.html">Transactions</a><a href="profile.html">Profile</a>` : ""}
        ${loggedIn ? `<button id="logout">Logout</button>` : `<a href="login.html">Login</a><a href="register.html">Register</a>`}
        <button id="theme-toggle">Theme</button>
      </div>
    </nav>
  `;

  document.getElementById("menu-btn")?.addEventListener("click", () =>
    document.getElementById("nav-links").classList.toggle("open")
  );

  document.getElementById("logout")?.addEventListener("click", () => {
    logout();
    window.location.href = "login.html";
  });

  document.getElementById("theme-toggle")?.addEventListener("click", () => {
    document.body.classList.toggle("dark");
    localStorage.setItem("theme", document.body.classList.contains("dark") ? "dark" : "light");
  });

  if (localStorage.getItem("theme") === "dark") document.body.classList.add("dark");
};

const renderServices = () => {
  const list = document.getElementById("service-list");
  if (!list) return;
  list.innerHTML = services.map(([name, description]) =>
    `<div class="card"><h3>${name}</h3><p>${description}</p></div>`
  ).join("");
};

const dashboard = () => {
  if (pageName !== "dashboard.html") return;
  if (!protectPage()) return;

  const customer = currentCustomer();
  const account = getAccounts().find(a => a.accountNumber === customer?.accountNumber);
  const transactions = getAccountTransactions(account?.accountNumber);
  const credits = transactions.filter(t => t.type === "Credit").reduce((sum, t) => sum + t.amount, 0);
  const debits = transactions.filter(t => t.type === "Debit").reduce((sum, t) => sum + t.amount, 0);

  document.getElementById("welcome").textContent = `Welcome, ${customer?.firstName || "Customer"}`;
  document.getElementById("summary").innerHTML = [
    ["Account Number", account?.accountNumber || "-"],
    ["Available Balance", money(account?.balance)],
    ["Total Deposits", money(credits)],
    ["Total Withdrawals", money(debits)],
    ["Transactions", transactions.length]
  ].map(([title, value]) => `<div class="stat"><h3>${title}</h3><p>${value}</p></div>`).join("");

  const recent = [...transactions].sort((a,b) => b.date.localeCompare(a.date)).slice(0, 5);
  document.getElementById("recent-transactions").innerHTML = recent.map(t =>
    `<tr><td>${t.date}</td><td>${t.id}</td><td>${t.type}</td><td>${t.description}</td><td>${money(t.amount)}</td><td>${money(t.balance)}</td></tr>`
  ).join("") || `<tr><td colspan="6">No transactions found.</td></tr>`;
};

const accountsPage = () => {
  if (pageName !== "accounts.html") return;
  if (!protectPage()) return;

  const customer = currentCustomer();
  const account = getAccounts().find(a => a.accountNumber === customer?.accountNumber);
  let hidden = false;

  const render = () => {
    document.getElementById("account-list").innerHTML = account ? `
      <div class="account-card">
        <p class="eyebrow">${account.type}</p>
        <p class="account-number">${account.accountNumber}</p>
        <p class="balance">${hidden ? "••••••" : money(account.balance)}</p>
        <p>Status: ${account.status}</p>
      </div>` : "<p>No account found.</p>";
  };
  render();

  document.getElementById("toggle-balance")?.addEventListener("click", event => {
    hidden = !hidden;
    event.target.textContent = hidden ? "Show Balances" : "Hide Balances";
    render();
  });

  document.getElementById("deposit-form")?.addEventListener("submit", event => {
    event.preventDefault();
    const accountNumber = document.getElementById("depositAccount").value.trim();
    const amount = Number(document.getElementById("depositAmount").value);
    const target = getAccounts().find(a => a.accountNumber === accountNumber && a.customerId === customer?.customerId);
    if (!target || amount <= 0) {
      showMessage("Invalid account or amount.", "error");
      return;
    }
    const accountObject = new Account(target.accountNumber, target.type, target.balance, target.customerId, target.status);
    accountObject.deposit(amount);
    const accounts = getAccounts().map(a => a.accountNumber === accountNumber ? {...a, balance: accountObject.balance} : a);
    saveAccounts(accounts);
    const txn = createTransaction(accountNumber, "Credit", amount, document.getElementById("depositDescription").value);
    updateTransactionBalance(txn.id, accountObject.balance);
    showMessage("Money deposited successfully.");
    event.target.reset();
    Object.assign(account, accountObject);
    render();
  });

  document.getElementById("withdraw-form")?.addEventListener("submit", event => {
    event.preventDefault();
    const accountNumber = document.getElementById("withdrawAccount").value.trim();
    const amount = Number(document.getElementById("withdrawAmount").value);
    const target = getAccounts().find(a => a.accountNumber === accountNumber && a.customerId === customer?.customerId);
    if (!target || amount <= 0) {
      showMessage("Invalid account or amount.", "error");
      return;
    }
    const accountObject = new Account(target.accountNumber, target.type, target.balance, target.customerId, target.status);
    if (!accountObject.withdraw(amount)) {
      showMessage("Insufficient balance.", "error");
      return;
    }
    saveAccounts(getAccounts().map(a => a.accountNumber === accountNumber ? {...a, balance: accountObject.balance} : a));
    const txn = createTransaction(accountNumber, "Debit", amount, document.getElementById("withdrawDescription").value);
    updateTransactionBalance(txn.id, accountObject.balance);
    showMessage("Withdrawal completed successfully.");
    event.target.reset();
    Object.assign(account, accountObject);
    render();
  });
};

const transactionsPage = () => {
  if (pageName !== "transactions.html") return;
  if (!protectPage()) return;

  const customer = currentCustomer();
  const accountNumber = customer?.accountNumber;
  const render = () => {
    let transactions = getAccountTransactions(accountNumber);
    const search = document.getElementById("search").value.toLowerCase();
    const type = document.getElementById("typeFilter").value;
    const min = Number(document.getElementById("minAmount").value || 0);
    const max = Number(document.getElementById("maxAmount").value || Infinity);
    const date = document.getElementById("dateFilter").value;
    const sort = document.getElementById("sortFilter").value;

    transactions = transactions
      .filter(t => `${t.id} ${t.description}`.toLowerCase().includes(search))
      .filter(t => !type || t.type === type)
      .filter(t => t.amount >= min && t.amount <= max)
      .filter(t => !date || t.date === date)
      .sort((a,b) => {
        if (sort === "oldest") return a.date.localeCompare(b.date);
        if (sort === "highest") return b.amount - a.amount;
        if (sort === "lowest") return a.amount - b.amount;
        return b.date.localeCompare(a.date);
      });

    const stats = transactionStats(transactions);
    document.getElementById("metrics").innerHTML = [
      ["Total", money(stats.total)], ["Credits", money(stats.credits)],
      ["Debits", money(stats.debits)], ["Average", money(stats.average)],
      ["Highest", money(stats.highest)], ["Lowest", money(stats.lowest)]
    ].map(([title, value]) => `<div class="stat"><h3>${title}</h3><p>${value}</p></div>`).join("");

    document.getElementById("transaction-body").innerHTML = transactions.map(t =>
      `<tr><td>${t.date}</td><td>${t.id}</td><td>${t.type}</td><td>${t.description}</td><td>${money(t.amount)}</td><td>${money(t.balance)}</td></tr>`
    ).join("") || `<tr><td colspan="6">No transactions found.</td></tr>`;
  };

  ["search","typeFilter","minAmount","maxAmount","dateFilter","sortFilter"].forEach(id =>
    document.getElementById(id)?.addEventListener("input", render)
  );
  render();
};

const profilePage = () => {
  if (pageName !== "profile.html") return;
  if (!protectPage()) return;
  const customer = currentCustomer();
  const fields = {
    profileCustomerId: customer?.customerId,
    profileName: customer?.fullName,
    profileEmail: customer?.email,
    profileMobile: customer?.mobile,
    profileAddress: customer?.address,
    profileCity: customer?.city,
    profileState: customer?.state,
    profilePin: customer?.pin
  };
  Object.entries(fields).forEach(([id, value]) => {
    const element = document.getElementById(id);
    if (element) element.value = value || "";
  });

  document.getElementById("profile-form").addEventListener("submit", event => {
    event.preventDefault();
    const mobile = document.getElementById("profileMobile").value.trim();
    const pin = document.getElementById("profilePin").value.trim();
    if (!/^\d{10}$/.test(mobile) || !/^\d{6}$/.test(pin)) {
      showMessage("Enter a valid 10-digit mobile number and 6-digit PIN.", "error");
      return;
    }
    const customers = getCustomers().map(c => c.username === getLoggedInUser()
      ? {...c, mobile, address: document.getElementById("profileAddress").value.trim(),
         city: document.getElementById("profileCity").value.trim(),
         state: document.getElementById("profileState").value.trim(), pin}
      : c);
    saveCustomers(customers);
    showMessage("Profile updated successfully.");
  });
};

const init = async () => {
  await seedData();
  renderHeader();
  renderServices();
  setupLogin();
  setupRegistration();
  setupTransfer();
  setupLoanCalculator();
  dashboard();
  accountsPage();
  transactionsPage();
  profilePage();
};

init();
