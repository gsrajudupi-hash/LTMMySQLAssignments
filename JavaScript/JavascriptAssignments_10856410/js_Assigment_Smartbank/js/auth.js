
import { KEYS, read, write, setSessionUser, getSessionUser, nextAccountNumber, currentCustomer, seedDemoData } from "./storage.js";

seedDemoData();

const showPassword = () => {
  document.querySelectorAll("[data-toggle-password], [data-toggle-field]").forEach(button => {
    button.addEventListener("click", () => {
      const id = button.dataset.togglePassword || button.dataset.toggleField;
      const input = document.getElementById(id) || document.querySelector(`[name="${id}"]`);
      if (!input) return;
      input.type = input.type === "password" ? "text" : "password";
      button.textContent = input.type === "password" ? "Show" : "Hide";
    });
  });
};

const loginForm = document.querySelector("#loginForm");
if (loginForm) {
  if (getSessionUser()) location.href = "dashboard.html";
  showPassword();
  loginForm.addEventListener("submit", event => {
    event.preventDefault();
    const username = loginForm.username.value.trim();
    const password = loginForm.password.value;
    const userError = document.querySelector('[data-error="username"]');
    const passError = document.querySelector('[data-error="password"]');
    userError.textContent = username ? "" : "Username is required.";
    passError.textContent = password ? "" : "Password is required.";
    if (!username || !password) return;
    const user = read(KEYS.customers, []).find(customer => customer.username === username && customer.password === password);
    const message = document.querySelector("#loginMessage");
    if (!user) {
      message.className = "form-message error-message";
      message.textContent = "Invalid username or password. Try demo / Smart@123.";
      return;
    }
    setSessionUser(username);
    message.className = "form-message success";
    message.textContent = "Login successful. Redirecting…";
    setTimeout(() => location.href = "dashboard.html", 500);
  });
}

const registerForm = document.querySelector("#registerForm");
if (registerForm) {
  showPassword();
  const validateField = (input, message) => {
    const small = input.parentElement.querySelector(".error");
    input.classList.toggle("invalid", Boolean(message));
    if (small) small.textContent = message || "";
    return !message;
  };
  registerForm.addEventListener("submit", event => {
    event.preventDefault();
    const data = Object.fromEntries(new FormData(registerForm).entries());
    const errors = {};
    const customers = read(KEYS.customers, []);
    if (!data.firstName.trim()) errors.firstName = "First name is required.";
    if (!data.lastName.trim()) errors.lastName = "Last name is required.";
    if (!/^\S+@\S+\.\S+$/.test(data.email)) errors.email = "Enter a valid email.";
    if (!/^\d{10}$/.test(data.mobile)) errors.mobile = "Enter a 10-digit mobile number.";
    if (!/^\d{6}$/.test(data.pin)) errors.pin = "Enter a 6-digit PIN.";
    if (data.dob && new Date(data.dob) > new Date()) errors.dob = "Date of birth cannot be in the future.";
    if (Number(data.initialDeposit) < 1000) errors.initialDeposit = "Minimum initial deposit is ₹1,000.";
    if (data.password.length < 8) errors.password = "Password must contain at least 8 characters.";
    if (data.password !== data.confirmPassword) errors.confirmPassword = "Passwords do not match.";
    if (customers.some(customer => customer.username.toLowerCase() === data.username.toLowerCase())) errors.username = "Username already exists.";
    Object.entries(data).forEach(([key]) => {
      const input = registerForm.elements[key];
      if (input && key !== "customerId" && key !== "gender" && key !== "accountType") validateField(input, errors[key] || "");
    });
    if (Object.keys(errors).length) {
      document.querySelector("#registerMessage").className = "form-message error-message";
      document.querySelector("#registerMessage").textContent = "Please correct the highlighted fields.";
      return;
    }
    const customerId = data.customerId.trim() || `CUST${String(customers.length + 1).padStart(3,"0")}`;
    const customer = {...data, customerId};
    delete customer.confirmPassword;
    customers.push(customer);
    write(KEYS.customers, customers);
    const accounts = read(KEYS.accounts, []);
    accounts.push({accountNumber: nextAccountNumber(), customerId, type:data.accountType, balance:Number(data.initialDeposit), status:"Active"});
    write(KEYS.accounts, accounts);
    const transactions = read(KEYS.transactions, []);
    transactions.push({id:`TXN${new Date().getTime()}`, accountNumber:accounts.at(-1).accountNumber, type:"Credit", description:"Initial deposit", amount:Number(data.initialDeposit), date:new Date().toISOString().slice(0,10), balance:Number(data.initialDeposit)});
    write(KEYS.transactions, transactions);
    const message = document.querySelector("#registerMessage");
    message.className = "form-message success";
    message.textContent = "Account created successfully. Redirecting to login…";
    setTimeout(() => location.href = "login.html", 900);
  });
}
