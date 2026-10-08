import {
  getAccounts, saveAccounts, getBeneficiaries, saveBeneficiaries,
  getLoggedInUser, currentCustomer, showMessage, money
} from "./storage.js";
import { createTransaction, updateTransactionBalance } from "./transaction.js";
import { verifyAccount } from "./auth.js";

export const setupTransfer = () => {
  const form = document.getElementById("transfer-form");
  if (!form) return;

  form.addEventListener("submit", async event => {
    event.preventDefault();
    const fromAccount = document.getElementById("fromAccount").value.trim();
    const beneficiaryAccount = document.getElementById("beneficiaryAccount").value.trim();
    const beneficiaryName = document.getElementById("beneficiaryName").value.trim();
    const amount = Number(document.getElementById("transferAmount").value);
    const transferType = document.getElementById("transferType").value;
    const remarks = document.getElementById("remarks").value.trim() || "Fund Transfer";

    const customer = currentCustomer();
    const accounts = getAccounts();
    const source = accounts.find(({ accountNumber, customerId }) =>
      accountNumber === fromAccount && customerId === customer?.customerId
    );
    const beneficiary = accounts.find(({ accountNumber }) => accountNumber === beneficiaryAccount);

    if (!source) {
      showMessage("Invalid source account.", "error");
      return;
    }
    if (!beneficiary || beneficiary.accountNumber === source.accountNumber) {
      showMessage("Invalid beneficiary account.", "error");
      return;
    }
    if (amount <= 0) {
      showMessage("Amount must be greater than zero.", "error");
      return;
    }
    if (amount > source.balance) {
      showMessage("Insufficient balance.", "error");
      return;
    }

    const limits = { IMPS: 200000, NEFT: 500000, RTGS: 1000000 };
    if (amount > limits[transferType]) {
      showMessage(`Maximum ${transferType} limit is ${money(limits[transferType])}.`, "error");
      return;
    }

    try {
      await verifyAccount(beneficiaryAccount);
      const confirmation = document.getElementById("confirmation");
      confirmation.classList.remove("hidden");
      confirmation.innerHTML = `
        <h3>Confirm Transfer</h3>
        <p><b>From:</b> ${source.accountNumber}</p>
        <p><b>To:</b> ${beneficiary.accountNumber}</p>
        <p><b>Beneficiary:</b> ${beneficiaryName}</p>
        <p><b>Amount:</b> ${money(amount)}</p>
        <p><b>Type:</b> ${transferType}</p>
        <button id="confirm-transfer" class="btn">Confirm Transfer</button>
      `;

      document.getElementById("confirm-transfer").addEventListener("click", () => {
        source.balance -= amount;
        beneficiary.balance += amount;
        saveAccounts([...accounts]);

        const debit = createTransaction(source.accountNumber, "Debit", amount, remarks, { transferType });
        updateTransactionBalance(debit.id, source.balance);
        const credit = createTransaction(beneficiary.accountNumber, "Credit", amount, `Transfer from ${source.accountNumber}`, { transferType });
        updateTransactionBalance(credit.id, beneficiary.balance);

        const beneficiaries = getBeneficiaries();
        if (!beneficiaries.some(item => item.accountNumber === beneficiary.accountNumber)) {
          saveBeneficiaries([...beneficiaries, { accountNumber: beneficiary.accountNumber, name: beneficiaryName }]);
        }

        confirmation.classList.add("hidden");
        form.reset();
        showMessage("Fund transfer completed successfully.", "success");
      });
    } catch (error) {
      showMessage(error.message, "error");
    }
  });
};
