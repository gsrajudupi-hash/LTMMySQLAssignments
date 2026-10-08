
import { customerAccounts, customerTransactions, currentCustomer, formatINR } from "./storage.js";

const render = () => {
  const customer = currentCustomer();
  const accounts = customerAccounts();
  const transactions = customerTransactions();
  if (!customer) return;
  // Calculate the total balance, total credits, and total debits for the dashboard display
  const balance = accounts.reduce((sum, account) => sum + Number(account.balance), 0);
  // Calculate the total credits and debits from the transaction history
  const credits = transactions.filter(t => t.type === "Credit").reduce((sum,t) => sum + t.amount, 0);
  // Calculate the total debits from the transaction history
  const debits = transactions.filter(t => t.type !== "Credit").reduce((sum,t) => sum + t.amount, 0);
  document.querySelector("#welcomeName").textContent = customer.firstName;
  document.querySelector("#balanceValue").textContent = formatINR(balance);
  document.querySelector("#accountRef").textContent = accounts[0]?.accountNumber || "No account";
  document.querySelector("#totalDeposits").textContent = formatINR(credits);
  document.querySelector("#totalWithdrawals").textContent = formatINR(debits);
  document.querySelector("#transactionCount").textContent = transactions.length;
  // Render the recent transactions section
  const recent = document.querySelector("#recentTransactions");
  recent.innerHTML = transactions.slice(0,5).map(txn => `
    <div class="transaction-item">
      <span class="txn-icon ${txn.type.toLowerCase()}">${txn.type === "Credit" ? "↗" : "↘"}</span>
      <div><b>${txn.description}</b><small>${txn.id} · ${txn.date}</small></div>
      <span class="amount ${txn.type.toLowerCase()}">${txn.type === "Credit" ? "+" : "-"}${formatINR(txn.amount)}</span>
    </div>`).join("");
  const monthly = transactions.reduce((acc, txn) => {
    const key = txn.date.slice(5,7);
    acc[key] = (acc[key] || 0) + txn.amount;
    return acc;
  }, {});
  const max = Math.max(...Object.values(monthly), 1);
  document.querySelector("#insightBars").innerHTML = Object.entries(monthly).map(([month,total]) =>
    `<div class="bar-item"><div class="bar" style="height:${Math.max(12,(total/max)*105)}px"></div><small>${month}</small></div>`).join("");
};

// Handle the visibility toggle for the account balance
let hidden = false;
document.querySelector("#balanceToggle")?.addEventListener("click", () => {
  hidden = !hidden;
  const value = document.querySelector("#balanceValue");
  value.textContent = hidden ? "₹ ••••••" : formatINR(customerAccounts().reduce((sum,a)=>sum+a.balance,0));
});
render();
