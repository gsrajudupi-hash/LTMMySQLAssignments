import { store } from "./storage.js";
import { Account } from "./models.js";
import {
  addTransaction,
  getTransactions,
  summarize,
  query,
  renderRows,
} from "./transaction.js";
import { requireAuth, currentCustomer, userAccounts } from "./auth.js";
import { $, money, notify, showErrors, formValues } from "./ui.js";
import { checkAccount, delay } from "./api.js";

export const findAccount = (number) => {
  const acc = store.get("accounts").find((a) => a.accountNumber === number);
  if (!acc) throw new Error("Account not found");
  return Account.from(acc);
};
export const saveAccounts = (...accs) => {
  // rest parameters
  const updated = new Map(accs.map((a) => [a.accountNumber, { ...a }]));
  store.set(
    "accounts",
    store.get("accounts").map((a) => updated.get(a.accountNumber) ?? a),
  );
};
export function processDeposit(
  accountNumber,
  amount,
  depositType = "Cash",
  description = "",
) {
  const acc = findAccount(accountNumber);
  acc.deposit(amount);
  saveAccounts(acc);
  return addTransaction({
    accountNumber,
    type: "Credit",
    category: "Deposit",
    description: description || `${depositType} deposit`,
    amount,
    balance: acc.balance,
  });
}
export function processWithdrawal(accountNumber, amount, description = "") {
  const acc = findAccount(accountNumber);
  acc.withdraw(amount);
  saveAccounts(acc);
  return addTransaction({
    accountNumber,
    type: "Debit",
    category: "Withdrawal",
    description: description || "Cash withdrawal",
    amount,
    balance: acc.balance,
  });
}
export function initDashboard() {
  if (!requireAuth()) return;
  const me = currentCustomer(),
    accounts = userAccounts();
  const txs = getTransactions(accounts.map((a) => a.accountNumber));
  const { credits, debits, count } = summarize(txs);
  $("#welcome").textContent = `Welcome, ${me.fullName}`;
  $("#acc-nums").textContent = accounts.map((a) => a.accountNumber).join(", ");
  $("#balance").textContent = money(
    accounts.reduce((sum, a) => sum + a.balance, 0),
  );
  $("#deposits").textContent = money(credits);
  $("#withdrawals").textContent = money(debits);
  $("#count").textContent = count;
  renderRows($("#recent-body"), query(txs).slice(0, 5));
}
export function initAccounts() {
  if (!requireAuth()) return;
  let hidden = false;
  const accounts = userAccounts();
  document
    .querySelectorAll("select[name=accountNumber]")
    .forEach(
      (s) =>
        (s.innerHTML = accounts
          .map((a) => `<option>${a.accountNumber}</option>`)
          .join("")),
    );
  const draw = () => {
    const me = currentCustomer();
    $("#acc-list").innerHTML = userAccounts()
      .map(
        (a) => `<article class="card">
      <h3>${a.accountNumber}</h3><p>${a.type} Account <span class="badge">${a.status}</span></p>
      <p class="big">${hidden ? "₹ ••••••" : money(a.balance)}</p>
      <button class="ghost" data-details="${a.accountNumber}">View details</button>
      <div id="d-${a.accountNumber}" hidden><br>Owner: ${me.fullName}<br>Customer ID: ${a.customerId}<br>Transactions: ${getTransactions([a.accountNumber]).length}</div></article>`,
      )
      .join("");
  };
  $("#acc-list").addEventListener("click", (e) => {
    const id = e.target.dataset.details;
    if (id) {
      const box = document.getElementById(`d-${id}`);
      box.hidden = !box.hidden;
    }
  });
  $("#toggle-bal").addEventListener("click", (e) => {
    hidden = !hidden;
    e.target.textContent = hidden ? "Show balances" : "Hide balances";
    draw();
  });

  $("#deposit-form").addEventListener("submit", async (e) => {
    e.preventDefault();
    const form = e.target,
      { accountNumber, amount, depositType, description } = formValues(form),
      amt = Number(amount);
    if (
      !showErrors(
        form,
        amt > 0 ? {} : { amount: "Amount must be greater than zero." },
      )
    )
      return;
    try {
      await checkAccount(accountNumber); // async #1
      processDeposit(accountNumber, amt, depositType, description);
      notify("Money deposited successfully");
      form.reset();
      draw();
    } catch (err) {
      notify(err.message, "error");
    }
  });
  $("#withdraw-form").addEventListener("submit", async (e) => {
    e.preventDefault();
    const form = e.target,
      { accountNumber, amount, description } = formValues(form),
      amt = Number(amount);
    if (
      !showErrors(
        form,
        amt > 0 ? {} : { amount: "Amount must be greater than zero." },
      )
    )
      return;
    try {
      await delay(700); // async #2
      processWithdrawal(accountNumber, amt, description);
      notify("Money withdrawn successfully");
      form.reset();
      draw();
    } catch (err) {
      notify(
        err.message,
        err.message === "Insufficient balance" ? "warning" : "error",
      );
    }
  });
  draw();
}
