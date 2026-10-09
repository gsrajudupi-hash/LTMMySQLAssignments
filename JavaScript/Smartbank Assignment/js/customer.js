import { store, nextAccountNumber } from "./storage.js";
import { Account } from "./models.js";
import { addTransaction } from "./transaction.js";
import { requireAuth, currentCustomer } from "./auth.js";
import { $, notify, showErrors, formValues } from "./ui.js";
import { delay } from "./api.js";

export const MIN_DEPOSIT = 1000;
const isEmail = (v) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v);
const filled = (v) => String(v ?? "").trim() !== "";

export function validateContact(d) {
  const e = {};
  ["email", "mobile", "address", "city", "state", "pin"].forEach((k) => {
    if (!filled(d[k])) e[k] = "This field is required.";
  });
  if (!e.email && !isEmail(d.email)) e.email = "Enter a valid email address.";
  if (!e.mobile && !/^[6-9]\d{9}$/.test(d.mobile))
    e.mobile = "Mobile must be 10 digits starting with 6-9.";
  if (!e.pin && !/^\d{6}$/.test(d.pin)) e.pin = "PIN code must be 6 digits.";
  return e;
}
export function validateRegistration(d, customers = store.get("customers")) {
  const e = { ...validateContact(d) };
  [
    "customerId",
    "firstName",
    "lastName",
    "dob",
    "gender",
    "accountType",
    "username",
  ].forEach((k) => {
    if (!filled(d[k])) e[k] = "This field is required.";
  });
  if (!e.dob) {
    const dob = new Date(d.dob),
      age = (Date.now() - dob) / (365.25 * 24 * 3600 * 1000);
    if (dob > new Date()) e.dob = "Date of birth cannot be in the future.";
    else if (age < 18) e.dob = "You must be at least 18 years old.";
  }
  if (
    !e.customerId &&
    customers.some(
      (c) => c.customerId.toLowerCase() === d.customerId.toLowerCase(),
    )
  )
    e.customerId = "Customer ID already exists.";
  if (!e.username && customers.some((c) => c.username === d.username))
    e.username = "Username is taken.";
  if (!(Number(d.deposit) >= MIN_DEPOSIT))
    e.deposit = `Minimum initial deposit is ₹${MIN_DEPOSIT}.`;
  if ((d.password ?? "").length < 8)
    e.password = "Password must be at least 8 characters.";
  if (d.confirmPassword !== d.password || !filled(d.confirmPassword))
    e.confirmPassword = "Passwords do not match.";
  return e;
}

export function registerCustomer(data) {
  const { confirmPassword, deposit, accountType, ...customer } = data; // destructuring + rest
  const account = new Account(
    nextAccountNumber(),
    customer.customerId,
    accountType,
    Number(deposit),
  );
  store.set("customers", [...store.get("customers"), customer]);
  store.set("accounts", [...store.get("accounts"), { ...account }]);
  addTransaction({
    accountNumber: account.accountNumber,
    type: "Credit",
    category: "Deposit",
    description: "Initial deposit",
    amount: account.balance,
    balance: account.balance,
  });
  return account;
}
export function initRegister() {
  const form = $("#register-form");
  form.elements.dob.max = new Date().toLocaleDateString("en-CA");
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    if (!showErrors(form, validateRegistration(formValues(form))))
      return notify("Please fix the highlighted fields", "warning");
    const btn = form.querySelector("button[type=submit]");
    btn.disabled = true;
    try {
      await delay(800);
      const acc = registerCustomer(formValues(form));
      notify(`Registration successful! Account number: ${acc.accountNumber}`);
      setTimeout(() => (location.href = "login.html"), 2000);
    } catch (err) {
      notify(err.message, "error");
      btn.disabled = false;
    }
  });
}
export function initProfile() {
  if (!requireAuth()) return;
  const form = $("#profile-form"),
    me = currentCustomer();
  $("#p-name").textContent = me.fullName;
  $("#p-id").textContent = me.customerId;
  ["email", "mobile", "address", "city", "state", "pin"].forEach(
    (k) => (form.elements[k].value = me[k]),
  );
  form.addEventListener("submit", (e) => {
    e.preventDefault();
    const { email, mobile, address, city, state, pin } = formValues(form);
    if (
      !showErrors(
        form,
        validateContact({ email, mobile, address, city, state, pin }),
      )
    )
      return notify("Please fix the highlighted fields", "warning");
    store.set(
      "customers",
      store
        .get("customers")
        .map((c) =>
          c.customerId === me.customerId
            ? { ...c, email, mobile, address, city, state, pin }
            : c,
        ),
    );
    notify("Profile updated successfully");
  });
}
