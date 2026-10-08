
import { customerAccounts, customerTransactions, formatINR, read, write, KEYS, nextTransactionId } from "./storage.js";
import { Account } from "./models.js";
import { notify } from "./app.js";

let hidden = false;
const render = () => {
  const accounts = customerAccounts();
  const container = document.querySelector("#accountCards");
  container.innerHTML = accounts.map((account, index) => `
    <article class="account-card">
      <span class="eyebrow">SMARTBANK ${account.type.toUpperCase()}</span>
      <h3>${account.type} Account</h3>
      <span class="account-number">${account.accountNumber}</span>
      <div class="account-balance">${hidden ? "₹ ••••••" : formatINR(account.balance)}</div>
      <div class="account-footer"><div>STATUS<b>● ${account.status}</b></div><div>ACCOUNT HOLDER<b>Primary customer</b></div><button class="btn btn-light" data-account="${account.accountNumber}">Details</button></div>
    </article>`).join("");
  document.querySelectorAll("[data-account]").forEach(btn => btn.addEventListener("click", () => showDetails(btn.dataset.account)));
  if (accounts[0]) showDetails(accounts[0].accountNumber);
};
const showDetails = number => {
  const account = customerAccounts().find(a => a.accountNumber === number);
  const transactions = customerTransactions().filter(t => t.accountNumber === number);
  if (!account) return;
  const credits = transactions.filter(t => t.type === "Credit").reduce((s, t) => s + t.amount, 0);
  const debits = transactions.filter(t => t.type !== "Credit").reduce((s, t) => s + t.amount, 0);
  document.querySelector("#accountDetailBody").innerHTML = [
    ["Account number", account.accountNumber], ["Account type", account.type], ["Current balance", hidden ? "Hidden" : formatINR(account.balance)], ["Status", account.status],
    ["Total credits", formatINR(credits)], ["Total debits", formatINR(debits)], ["Transactions", transactions.length], ["Visibility", "User controlled"]
  ].map(([label, value]) => `<div><span>${label}</span><b>${value}</b></div>`).join("");
};
document.querySelector("#globalBalanceToggle")?.addEventListener("click", event => {
  hidden = !hidden; event.currentTarget.textContent = hidden ? "◉ Show balances" : "◉ Hide balances"; render();
});
render();
 
// Operation account selection and money form handling
const operationAccount = document.querySelector("#operationAccount");
if (operationAccount) {
  operationAccount.innerHTML = customerAccounts().map(a => `<option value="${a.accountNumber}">${a.accountNumber} · ${a.type}</option>`).join("");
}
document.querySelector("#moneyForm")?.addEventListener("submit", event => {
  event.preventDefault();
  const data = Object.fromEntries(new FormData(event.currentTarget).entries());
  const amount = Number(data.amount);
  const accounts = read(KEYS.accounts, []);
  const raw = accounts.find(a => a.accountNumber === data.accountNumber);
  if (!raw || amount <= 0) { notify("Enter a valid amount.", "error"); return; }
  // Initialize the account object for the operation
  const account = new Account(raw.accountNumber, raw.type, raw.balance);
  // Perform the deposit or withdrawal operation
  const success = data.operationType === "deposit" ? (account.deposit(amount), true) : account.withdraw(amount);
  if (!success) { notify("Insufficient balance for withdrawal.", "error"); return; }
  raw.balance = account.balance;
  write(KEYS.accounts, accounts);
  // Record the transaction in the transaction history
  const transactions = read(KEYS.transactions, []);
  transactions.push({
    id: nextTransactionId(), accountNumber: raw.accountNumber,
    type: data.operationType === "deposit" ? "Credit" : "Debit",
    description: data.description?.trim() || (data.operationType === "deposit" ? "Cash deposit" : "Cash withdrawal"),
    amount, date: new Date().toISOString().slice(0, 10), balance: raw.balance
  });
  write(KEYS.transactions, transactions);
  event.currentTarget.reset();
  if (operationAccount) operationAccount.value = raw.accountNumber;
  render();
  notify(data.operationType === "deposit" ? "Money deposited successfully." : "Money withdrawn successfully.");
});
