// localStorage helpers + ID generators
export const store = {
  get(key, fallback = []) {
    try {
      return JSON.parse(localStorage.getItem(key)) ?? fallback;
    } catch {
      return fallback;
    }
  },
  set: (key, value) => localStorage.setItem(key, JSON.stringify(value)),
};
export const esc = (s = "") =>
  String(s).replace(
    /[&<>"']/g,
    (c) =>
      ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;" })[
        c
      ],
  );
export const todayISO = () => new Date().toLocaleDateString("en-CA"); // YYYY-MM-DD
export const nextTxnId = () => {
  // TXN202610020001
  const n = store.get("txnCounter", 0) + 1;
  store.set("txnCounter", n);
  return `TXN${todayISO().replaceAll("-", "")}${String(n).padStart(4, "0")}`;
};
export const nextAccountNumber = () => {
  // SB100001
  const max = store
    .get("accounts")
    .reduce((m, a) => Math.max(m, Number(a.accountNumber.slice(2))), 100000);
  return `SB${max + 1}`;
};
