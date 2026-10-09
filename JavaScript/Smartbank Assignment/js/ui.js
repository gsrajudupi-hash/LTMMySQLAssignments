// Shared UI helpers: DOM shortcuts, notifications, validation messages, header/footer, theme
export const $ = (sel, root = document) => root.querySelector(sel);
export const money = (n) =>
  new Intl.NumberFormat("en-IN", { style: "currency", currency: "INR" }).format(
    n,
  );
export const formValues = (form) => Object.fromEntries(new FormData(form));

export function notify(message, type = "success") {
  let box = $("#toasts");
  if (!box) {
    box = Object.assign(document.createElement("div"), { id: "toasts" });
    document.body.append(box);
  }
  const toast = Object.assign(document.createElement("div"), {
    className: `toast ${type}`,
    textContent: message,
  });
  box.append(toast);
  setTimeout(() => toast.remove(), 3500);
}
export function showErrors(form, errors = {}) {
  form.querySelectorAll(".err").forEach((el) => (el.textContent = ""));
  Object.entries(errors).forEach(([name, msg]) => {
    const el = form.elements[name]?.closest("label")?.querySelector(".err");
    if (el) el.textContent = msg;
  });
  return Object.keys(errors).length === 0;
}
const LOGO = '<img src="../images/smart-logo.png">';
export function buildChrome({ loggedIn, onLogout }) {
  document.documentElement.dataset.theme =
    localStorage.getItem("theme") || "light";
  const links = loggedIn
    ? [
        ["dashboard.html", "Dashboard"],
        ["accounts.html", "Accounts"],
        ["transfer.html", "Transfer"],
        ["transactions.html", "Transactions"],
        ["loan.html", "Loans"],
        ["profile.html", "Profile"],
      ]
    : [
        ["index.html", "Home"],
        ["index.html#about", "About"],
        ["index.html#services", "Services"],
        ["loan.html", "Loans"],
        ["index.html#contact", "Contact"],
        ["login.html", "Login"],
        ["register.html", "Register"],
      ];
  $("#site-header").innerHTML =
    `<a class="brand" href="${loggedIn ? 'dashboard.html' : 'index.html'}">${LOGO} SmartBank</a>
    <button id="menu-btn" class="ghost" aria-label="Toggle menu">☰</button>
    <nav id="nav">${links.map(([h, t]) => `<a href="${h}">${t}</a>`).join("")}${loggedIn ? '<a href="#" id="logout">Logout</a>' : ""}
    </nav>`;
  $("#site-footer").innerHTML =
    `<p>© ${new Date().getFullYear()} SmartBank — All rights reserved.</p>`;
  $("#menu-btn").addEventListener("click", () =>
    $("#nav").classList.toggle("open"),
  );
  $("#logout")?.addEventListener("click", (e) => {
    e.preventDefault();
    onLogout();
  });
}
