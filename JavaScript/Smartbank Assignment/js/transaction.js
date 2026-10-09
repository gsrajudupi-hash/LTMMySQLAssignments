import { store, esc, nextTxnId, todayISO } from "./storage.js";
import { $, money, formValues } from "./ui.js";
import { requireAuth, userAccounts } from "./auth.js";

export const getTransactions = (accountNumbers) =>
  store
    .get("transactions")
    .filter((t) => accountNumbers.includes(t.accountNumber));

export function addTransaction({
  accountNumber,
  type,
  category,
  description = "",
  amount,
  balance,
}) {
  const tx = {
    id: nextTxnId(),
    accountNumber,
    type,
    category,
    description,
    amount,
    balance,
    date: todayISO(),
  };
  store.set("transactions", [...store.get("transactions"), tx]);
  return tx;
}
// reduce(): credits, debits, transfers, average, highest, lowest
export function summarize(txs) {
  const s = txs.reduce(
    (a, t) => ({
      credits: a.credits + (t.type === "Credit" ? t.amount : 0),
      debits: a.debits + (t.type === "Debit" ? t.amount : 0),
      transfers: a.transfers + (t.category === "Transfer" ? 1 : 0),
      high: Math.max(a.high, t.amount),
      low: Math.min(a.low, t.amount),
    }),
    { credits: 0, debits: 0, transfers: 0, high: 0, low: Infinity },
  );
  return {
    ...s,
    low: txs.length ? s.low : 0,
    count: txs.length,
    average: txs.length ? (s.credits + s.debits) / txs.length : 0,
  };
}
// filter() + sort()
export function query(
  txs,
  {
    q = "",
    type = "",
    min = 0,
    max = 0,
    from = "",
    to = "",
    sort = "newest",
  } = {},
) {
  const list = txs.filter(
    (t) =>
      (!q || t.id.toLowerCase().includes(q.trim().toLowerCase())) &&
      (!type || t.type === type) &&
      (!min || t.amount >= min) &&
      (!max || t.amount <= max) &&
      (!from || t.date >= from) &&
      (!to || t.date <= to),
  );
  const sorters = {
    newest: () => list.reverse().sort((a, b) => b.date.localeCompare(a.date)),
    oldest: () => list.sort((a, b) => a.date.localeCompare(b.date)),
    high: () => list.sort((a, b) => b.amount - a.amount),
    low: () => list.sort((a, b) => a.amount - b.amount),
  };
  return (sorters[sort] ?? sorters.newest)();
}
// map() + dynamic DOM rows
export function renderRows(tbody, txs) {
  if (!txs.length) {
    tbody.innerHTML = '<tr><td colspan="6">No transactions found.</td></tr>';
    return;
  }
  tbody.replaceChildren(...txs.map(({ date, id, type, description, amount, balance }) => {
      const row = document.createElement("tr");
      row.innerHTML = `<td>${date}</td><td>${id}</td><td class="${type.toLowerCase()}">${type}</td><td>${esc(description)}</td><td>${money(amount)}</td><td>${money(balance)}</td>`;
      return row;
    }),
  );
}

export function initTransactions() {
  if (!requireAuth()) return;
  const all = getTransactions(userAccounts().map((a) => a.accountNumber));
  const form = $("#filters");
  const draw = () => {
    const { q, type, min, max, from, to, sort } = formValues(form);
    const list = query(all, {
      q,
      type,
      min: Number(min) || 0,
      max: Number(max) || 0,
      from,
      to,
      sort,
    });
    renderRows($("#tx-body"), list);
    const s = summarize(list);
    $("#stats").innerHTML = [
      ["Total credits", money(s.credits)],
      ["Total debits", money(s.debits)],
      ["Transfers", s.transfers],
      ["Average", money(s.average)],
      ["Highest", money(s.high)],
      ["Lowest", money(s.low)],
    ]
      .map(
        ([k, v]) =>
          `<div class="card stat"><span>${k}</span><strong>${v}</strong></div>`,
      )
      .join("");
  };
  form.addEventListener("input", draw);
  form.addEventListener("reset", () => setTimeout(draw));
  draw();
}
