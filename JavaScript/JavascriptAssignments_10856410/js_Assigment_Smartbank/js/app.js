
import { KEYS, seedDemoData, getSessionUser, currentCustomer, clearSession } from "./storage.js";
import { fetchCustomers } from "./api.js";

const services = [
  ["◈","Savings Account","Save confidently with a simple everyday account."],
  ["▣","Current Account","Flexible banking for your day-to-day business."],
  ["◇","Fixed Deposit","Plan ahead with a predictable fixed return."],
  ["▤","Personal Loan","Flexible financing for life's important moments."],
  ["⌂","Home Loan","Turn your home plans into a clear repayment journey."],
  ["✦","Education Loan","Support education goals with flexible financing."],
  ["▰","Credit Card","Convenient spending with easy digital management."],
  ["◎","Internet Banking","Manage your banking experience from anywhere."]
];

const renderServices = () => {
  const grid = document.querySelector("#serviceGrid");
  if (!grid) return;
  grid.innerHTML = services.map(([icon,title,description]) => `
    <article class="service-card"><div class="service-icon">${icon}</div><h3>${title}</h3><p>${description}</p></article>
  `).join("");
};

const setupNavigation = () => {
  const toggle = document.querySelector("#navToggle");
  const nav = document.querySelector("#mainNav");
  toggle?.addEventListener("click", () => nav?.classList.toggle("open"));
  const mobileMenu = document.querySelector("#mobileMenu");
  const sidebar = document.querySelector("#sidebar");
  mobileMenu?.addEventListener("click", () => sidebar?.classList.toggle("open"));
};

const applyTheme = () => {
  const saved = localStorage.getItem(KEYS.theme) || "light";
  document.body.classList.toggle("dark", saved === "dark");
  const buttons = [document.querySelector("#themeToggle"), document.querySelector("#headerTheme")];
  buttons.forEach(button => { if (button) button.textContent = saved === "dark" ? "☀" : "☾"; });
};

const setupTheme = () => {
  applyTheme();
  const toggle = () => {
    const next = document.body.classList.contains("dark") ? "light" : "dark";
    localStorage.setItem(KEYS.theme, next);
    applyTheme();
  };
  document.querySelector("#themeToggle")?.addEventListener("click", toggle);
  document.querySelector("#headerTheme")?.addEventListener("click", toggle);
};

const fillHeaderUser = () => {
  const customer = currentCustomer();
  if (!customer) return;
  const initials = `${customer.firstName?.[0] || ""}${customer.lastName?.[0] || ""}`.toUpperCase();
  document.querySelectorAll("#headerName").forEach(el => el.textContent = `${customer.firstName} ${customer.lastName}`);
  document.querySelectorAll("#headerAvatar").forEach(el => el.textContent = initials);
};

const setupLogout = () => document.querySelectorAll("[data-logout]").forEach(button => button.addEventListener("click", () => {
  clearSession();
  location.href = "login.html";
}));

const protectAppPage = () => {
  const protectedPage = document.body.classList.contains("app-page");
  if (protectedPage && !getSessionUser()) location.href = "login.html";
};

seedDemoData();
protectAppPage();
renderServices();
// Fetch API + async/await demonstration required by the assignment.
fetchCustomers().then(data => console.debug("Sample customers loaded with Fetch API:", data)).catch(error => console.debug("Sample data fetch skipped:", error));
setupNavigation();
setupTheme();
fillHeaderUser();
setupLogout();

export const notify = (message, type = "success") => {
  const container = document.querySelector("#toastContainer");
  if (!container) return;
  const toast = document.createElement("div");
  toast.className = `toast ${type}`;
  toast.innerHTML = `<span>${type === "success" ? "✓" : type === "error" ? "!" : "!"}</span><b>${message}</b>`;
  container.appendChild(toast);
  setTimeout(() => toast.remove(), 3500);
};
