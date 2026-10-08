
import { customerAccounts, read, write, KEYS, formatINR, nextTransactionId } from "./storage.js";
import { notify } from "./app.js";
// Transfer form handling script
const form = document.querySelector("#transferForm");
const modal = document.querySelector("#confirmationModal");
let pending = null;
// Function to populate the "From Account" dropdown with the customer's accounts
const populateAccounts = () => {
  document.querySelector("#fromAccount").innerHTML = customerAccounts().map(a => `<option value="${a.accountNumber}">${a.accountNumber} · ${a.type} · ${formatINR(a.balance)}</option>`).join("");
};
  // verifyAccount function checks if the beneficiary account exists in the system
const verifyAccount = async number => new Promise((resolve,reject) => setTimeout(() => {
  const exists = read(KEYS.accounts, []).some(a => a.accountNumber === number);
  exists ? resolve("Account found") : reject("Invalid beneficiary account");
}, 650));

// Function to render the transfer confirmation modal
const renderConfirmation = data => {
  document.querySelector("#confirmationBody").innerHTML = [
    ["From account",data.fromAccount],["Beneficiary",`${data.beneficiaryName} (${data.beneficiaryAccount})`],
    ["Amount",formatINR(data.amount)],["Transfer type",data.transferType],["Remarks",data.remarks || "—"]
  ].map(([label,value])=>`<div class="confirm-row"><span>${label}</span><b>${value}</b></div>`).join("");
  modal.classList.remove("hidden");
};
//  submit events
form?.addEventListener("submit", async event => {
  event.preventDefault();
  const data = Object.fromEntries(new FormData(form).entries());
  const account = customerAccounts().find(a => a.accountNumber === data.fromAccount);
  const amount = Number(data.amount);
  const message = document.querySelector("#transferMessage");
  if (!account || !data.beneficiaryAccount || !data.beneficiaryName || amount <= 0) {
    message.className = "form-message error-message"; message.textContent = "Please enter all required transfer details."; return;
  }
  if (data.beneficiaryAccount === data.fromAccount) {
    message.className = "form-message error-message"; message.textContent = "Source and beneficiary accounts must be different."; return;
  }
  if (amount > account.balance) {
    message.className = "form-message error-message"; message.textContent = "Insufficient balance for this transfer."; return;
  }
  if (amount > 500000) {
    message.className = "form-message error-message"; message.textContent = "Transaction limit is ₹5,00,000."; return;
  }
   // check the status of the beneficiary account before proceeding with the transfer
  const status = document.querySelector("#verificationStatus");
  status.innerHTML = `<span>…</span><div><b>Verifying beneficiary…</b><small>Please wait while we check the account.</small></div>`;
  try {
    await verifyAccount(data.beneficiaryAccount);
    status.innerHTML = `<span style="color:var(--primary)">✓</span><div><b>Beneficiary verified</b><small>Account is ready for transfer.</small></div>`;
    pending = {...data, amount};
    renderConfirmation(pending);
  } catch (error) {
    status.innerHTML = `<span style="color:var(--danger)">!</span><div><b>Verification failed</b><small>${error}</small></div>`;
    message.className = "form-message error-message"; message.textContent = error;
  }
});
    // close modal function
const closeModal = () => modal.classList.add("hidden");
document.querySelector("#closeModal")?.addEventListener("click", closeModal);
document.querySelector("#cancelTransfer")?.addEventListener("click", closeModal);
document.querySelector("#confirmTransfer")?.addEventListener("click", () => {
  if (!pending) return;
  const accounts = read(KEYS.accounts, []);
  const source = accounts.find(a => a.accountNumber === pending.fromAccount);
  source.balance -= pending.amount;
  write(KEYS.accounts, accounts);
  const transactions = read(KEYS.transactions, []);
  transactions.push({id:nextTransactionId(), accountNumber:pending.fromAccount, type:"Transfer", description:`Transfer to ${pending.beneficiaryName}`, amount:pending.amount, date:new Date().toISOString().slice(0,10), balance:source.balance});
  write(KEYS.transactions, transactions);
  closeModal();
  form.reset();
  populateAccounts();
  notify("Fund transfer completed successfully.");
});
populateAccounts();
