import { store } from "./storage.js";
import { Account, Customer } from "./models.js";
import { $, notify, showErrors, formValues } from "./ui.js";

export const currentUsername = () => sessionStorage.getItem("loggedInUser");
export const currentCustomer = () => {
  const found = store
    .get("customers")
    .find((c) => c.username === currentUsername());
  return found ? new Customer(found) : null;
};

export const userAccounts = () => {
  const me = currentCustomer();
  return me
    ? store
        .get("accounts")
        .filter((a) => a.customerId === me.customerId)
        .map(Account.from)
    : [];
};
export function requireAuth() {
  if (currentUsername() && currentCustomer()) return true;
  location.replace("login.html");
  return false;
}
export function login(username, password) {
  const user = store
    .get("customers")
    .find((c) => c.username === username && c.password === password);
  if (!user) throw new Error("Invalid username or password");
  sessionStorage.setItem("loggedInUser", username);
}

export function logout() {
  sessionStorage.removeItem("loggedInUser");
  location.href = "login.html";
}

export function initLogin() {
  const form = $("#login-form");
  $("#show-pass").addEventListener(
    "change",
    (e) =>
      (form.elements.password.type = e.target.checked ? "text" : "password"),
  );
  form.addEventListener("submit", (e) => {
    e.preventDefault();
    const { username, password } = formValues(form);
    const errors = {};
    if (!username.trim()) errors.username = "Username is required.";
    if (password.length < 8)
      errors.password = "Password must be at least 8 characters.";
    if (!showErrors(form, errors)) return;
    try {
      login(username.trim(), password);
      notify("Login successful");
      setTimeout(() => (location.href = "dashboard.html"), 600);
    } catch (err) {
      notify(err.message, "error");
    }
  });
}
