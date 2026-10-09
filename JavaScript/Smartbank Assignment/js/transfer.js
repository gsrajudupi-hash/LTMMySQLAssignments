import { store, esc } from "./storage.js";
import { findAccount, saveAccounts } from "./account.js";
import { addTransaction } from "./transaction.js";
import { requireAuth, currentCustomer, userAccounts } from "./auth.js";
import { $, money, notify, showErrors, formValues } from "./ui.js";
import { checkAccount } from "./api.js";

export const LIMITS = {
  IMPS: { min: 1, max: 200000 },
  NEFT: { min: 1, max: 1000000 },
  RTGS: { min: 200000, max: 5000000 },
};

export function validateTransfer(
  { from, beneficiary, beneficiaryName, amount, type },
  accounts = userAccounts(),
) {
  const e = {},
    amt = Number(amount),
    src = accounts.find((a) => a.accountNumber === from);
  if (!src) e.from = "Select a valid source account.";
  if (!/^SB\d{6}$/.test(beneficiary ?? ""))
    e.beneficiary = "Enter a valid account number (e.g. SB100002).";
  else if (beneficiary === from)
    e.beneficiary = "Beneficiary must differ from the source account.";
  if (!beneficiaryName?.trim())
    e.beneficiaryName = "Beneficiary name is required.";
  if (!(amt > 0)) e.amount = "Amount must be greater than zero.";
  else {
    const { min, max } = LIMITS[type];
    if (amt < min || amt > max)
      e.amount = `${type} limit is ${money(min)} to ${money(max)}.`;
    else if (src && amt > src.balance) e.amount = "Insufficient balance.";
  }
  return e;
}
export function executeTransfer({
  from,
  to,
  name,
  amount,
  type,
  remarks = "",
}) {
  const src = findAccount(from),
    dst = findAccount(to);
  src.withdraw(amount);
  dst.deposit(amount);
  saveAccounts(src, dst);
  const debit = addTransaction({
    accountNumber: from,
    type: "Debit",
    category: "Transfer",
    description: `${type} to ${name} (${to})${remarks ? ` - ${remarks}` : ""}`,
    amount,
    balance: src.balance,
  });
  addTransaction({
    accountNumber: to,
    type: "Credit",
    category: "Transfer",
    description: `${type} from ${from}`,
    amount,
    balance: dst.balance,
  });
  return debit;
}

export function initTransfer() {
  if (!requireAuth()) return;
  const form = $("#transfer-form"),
    panel = $("#confirm"),
    me = currentCustomer();
  let pending = null;
  const myBenes = () =>
    store.get("beneficiaries").filter((b) => b.owner === me.customerId);
  const drawLists = () => {
    form.elements.from.innerHTML = userAccounts()
      .map(
        (a) =>
          `<option value="${a.accountNumber}">${a.accountNumber} (${a.type}) – ${money(a.balance)}</option>`,
      )
      .join("");
    $("#bene-list").innerHTML = myBenes()
      .map((b) => `<option value="${b.accountNumber}">${esc(b.name)}</option>`)
      .join("");
  };
  drawLists();
  form.elements.beneficiary.addEventListener("change", (e) => {
    const known = myBenes().find((b) => b.accountNumber === e.target.value);
    if (known) form.elements.beneficiaryName.value = known.name;
  });
  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const data = formValues(form);
    if (!showErrors(form, validateTransfer(data)))
      return notify("Please correct the highlighted fields", "warning");
    const btn = form.querySelector("button[type=submit]");
    btn.disabled = true;
    btn.textContent = "Verifying…";
    try {
      await checkAccount(data.beneficiary);
      pending = data;
      $("#summary").innerHTML =
        `<dl><dt>From</dt><dd>${data.from}</dd><dt>To</dt><dd>${data.beneficiary} (${esc(data.beneficiaryName)})</dd>
        <dt>Amount</dt><dd>${money(Number(data.amount))}</dd><dt>Mode</dt><dd>${data.type}</dd><dt>Remarks</dt><dd>${esc(data.remarks) || "—"}</dd></dl>`;
      form.hidden = true;
      panel.hidden = false;
    } catch {
      notify("Invalid beneficiary account", "error");
    } finally {
      btn.disabled = false;
      btn.textContent = "Continue";
    }
  });
  $("#cancel-btn").addEventListener("click", () => {
    panel.hidden = true;
    form.hidden = false;
  });
  
  $("#confirm-btn").addEventListener("click", () => {
    try {
      const { from, beneficiary, beneficiaryName, amount, type, remarks } =
        pending;
      const tx = executeTransfer({
        from,
        to: beneficiary,
        name: beneficiaryName.trim(),
        amount: Number(amount),
        type,
        remarks,
      });
      if (!myBenes().some((b) => b.accountNumber === beneficiary))
        store.set("beneficiaries", [
          ...store.get("beneficiaries"),
          {
            owner: me.customerId,
            accountNumber: beneficiary,
            name: beneficiaryName.trim(),
          },
        ]);
      notify(`Fund transfer completed (${tx.id})`);
      form.reset();
      drawLists();
      panel.hidden = true;
      form.hidden = false;
    } catch (err) {
      notify(err.message, "error");
    }
  });
}
