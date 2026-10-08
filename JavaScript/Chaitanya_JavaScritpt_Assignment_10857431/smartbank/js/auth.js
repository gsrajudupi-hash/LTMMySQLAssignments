import {
  getCustomers, saveCustomers, setLoggedInUser, getLoggedInUser,
  showMessage
} from "./storage.js";
import { Customer } from "./customer.js";
import { getAccounts, saveAccounts } from "./storage.js";
import { Account, generateAccountNumber } from "./account.js";
import { createTransaction, updateTransactionBalance } from "./transaction.js";

const checkAccount = accountNumber =>
  new Promise((resolve, reject) => {
    setTimeout(() => {
      accountNumber ? resolve("Account verified") : reject(new Error("Account not found"));
    }, 500);
  });

export const verifyAccount = async accountNumber => {
  try {
    return await checkAccount(accountNumber);
  } catch (error) {
    throw error;
  }
};

export const protectPage = () => {
  if (!getLoggedInUser()) {
    window.location.href = "login.html";
    return false;
  }
  return true;
};

export const setupLogin = () => {
  const form = document.getElementById("login-form");
  if (!form) return;

  document.getElementById("show-password")?.addEventListener("change", event => {
    document.getElementById("password").type = event.target.checked ? "text" : "password";
  });

  form.addEventListener("submit", async event => {
    event.preventDefault();
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;
    const customer = getCustomers().find(
      ({ username: user, password: pass }) => user === username && pass === password
    );

    if (!customer) {
      showMessage("Invalid username or password.", "error");
      return;
    }

    try {
      await verifyAccount(customer.accountNumber);
      setLoggedInUser(username);
      window.location.href = "dashboard.html";
    } catch (error) {
      showMessage(error.message, "error");
    }
  });
};

const validDOB = dob => {
  const date = new Date(dob);
  const today = new Date();
  let age = today.getFullYear() - date.getFullYear();
  const month = today.getMonth() - date.getMonth();
  if (month < 0 || (month === 0 && today.getDate() < date.getDate())) age--;
  return !Number.isNaN(date.getTime()) && date <= today && age >= 18;
};

export const setupRegistration = () => {
  const form = document.getElementById("register-form");
  if (!form) return;

  form.addEventListener("submit", event => {
    event.preventDefault();
    const value = id => document.getElementById(id).value.trim();
    const customerId = value("customerId");
    const username = value("regUsername");
    const password = document.getElementById("regPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const email = value("email");
    const mobile = value("mobile");
    const pin = value("pin");
    const deposit = Number(document.getElementById("initialDeposit").value);
    const dob = value("dob");
    const customers = getCustomers();

    const errors = [];
    if (customers.some(c => c.customerId === customerId)) errors.push("Customer ID already exists.");
    if (customers.some(c => c.username === username)) errors.push("Username already exists.");
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) errors.push("Enter a valid email.");
    if (!/^\d{10}$/.test(mobile)) errors.push("Mobile number must contain 10 digits.");
    if (!/^\d{6}$/.test(pin)) errors.push("PIN code must contain 6 digits.");
    if (password.length < 6) errors.push("Password must contain at least 6 characters.");
    if (password !== confirmPassword) errors.push("Passwords do not match.");
    if (deposit < 1000) errors.push("Minimum initial deposit is ₹1,000.");
    if (!validDOB(dob)) errors.push("Customer must be at least 18 years old with a valid date of birth.");

    if (errors.length) {
      showMessage(errors.join("<br>"), "error");
      return;
    }

    const accounts = getAccounts();
    const accountNumber = generateAccountNumber(accounts);
    const customer = new Customer({
      customerId, firstName: value("firstName"), lastName: value("lastName"), dob,
      gender: value("gender"), email, mobile, address: value("address"),
      city: value("city"), state: value("state"), pin, username, password, accountNumber
    });
    const account = new Account(accountNumber, value("accountType"), deposit, customerId);

    saveCustomers([...customers, customer]);
    saveAccounts([...accounts, account]);

    const txn = createTransaction(accountNumber, "Credit", deposit, "Initial Deposit");
    updateTransactionBalance(txn.id, deposit);

    showMessage("Registration successful. Redirecting to login...", "success");
    setTimeout(() => window.location.href = "login.html", 900);
  });
};
