import { buildChrome, $ } from "./ui.js";
import { seedData, loadDataThen } from "./api.js";
import { currentUsername, logout, initLogin } from "./auth.js";
import { initRegister, initProfile } from "./customer.js";
import { initDashboard, initAccounts } from "./account.js";
import { initTransactions } from "./transaction.js";
import { initTransfer } from "./transfer.js";
import { initLoan } from "./loan.js";

const SERVICES = [
  ["Savings Account", "Earn interest on everyday balances."],
  ["Current Account", "Built for business transactions."],
  ["Fixed Deposit", "Lock in a guaranteed return."],
  ["Personal Loan", "Quick funds for personal needs."],
  ["Home Loan", "Finance your dream home."],
  ["Education Loan", "Invest in your future."],
  ["Credit Card", "Rewards on every purchase."],
  ["Internet Banking", "Bank anywhere, anytime."],
];

function initHome() {
  $("#service-cards").innerHTML = SERVICES.map(
    ([name, desc]) =>
      `<article class="card"><h3>${name}</h3><p>${desc}</p></article>`,
  ).join("");
  loadDataThen()
    .then(
      ({ customers }) =>
        ($("#sample-count").textContent =
          `${customers.length} demo customers loaded from JSON — try username “${customers[0].username}” / password “${customers[0].password}”.`),
    )
    .catch((err) => console.warn("Could not load sample data", err));
}
const pages = {
  home: initHome,
  login: initLogin,
  register: initRegister,
  dashboard: initDashboard,
  accounts: initAccounts,
  transfer: initTransfer,
  transactions: initTransactions,
  loan: initLoan,
  profile: initProfile,
};

(async () => {
  try {
    await seedData();
  } catch (err) {
    console.error(err);
  }
  buildChrome({ loggedIn: Boolean(currentUsername()), onLogout: logout });
  pages[document.body.dataset.page]?.();
})();
