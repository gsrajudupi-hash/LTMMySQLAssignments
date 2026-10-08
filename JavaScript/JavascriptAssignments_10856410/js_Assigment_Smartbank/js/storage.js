// Storage utility functions for SmartBank application
export const KEYS = Object.freeze({
  customers: "smartbank_customers",
  accounts: "smartbank_accounts",
  transactions: "smartbank_transactions",
  beneficiaries: "smartbank_beneficiaries",
  theme: "smartbank_theme"
});

export const read = (key, fallback = []) => {
  try {
    const raw = localStorage.getItem(key);
    return raw ? JSON.parse(raw) : fallback;
  } catch {
    return fallback;
  }
};

export const write = (key, value) => localStorage.setItem(key, JSON.stringify(value));
export const getSessionUser = () => sessionStorage.getItem("loggedInUser");
export const setSessionUser = username => sessionStorage.setItem("loggedInUser", username);
export const clearSession = () => sessionStorage.removeItem("loggedInUser");

export const currentCustomer = () => {
  const username = getSessionUser();
  return read(KEYS.customers, []).find(customer => customer.username === username);
};

export const customerAccounts = () => {
  const customer = currentCustomer();
  return customer ? read(KEYS.accounts, []).filter(account => account.customerId === customer.customerId) : [];
};

export const customerTransactions = () => {
  const accounts = customerAccounts().map(({ accountNumber }) => accountNumber);
  return read(KEYS.transactions, []).filter(txn => accounts.includes(txn.accountNumber)).sort((a,b) => new Date(b.date) - new Date(a.date));
};

export const seedDemoData = () => {
  if (!localStorage.getItem(KEYS.customers)) {
    write(KEYS.customers, [{
      customerId:"CUST001", firstName:"Sanjay", lastName:"Chandra", dob:"1995-05-18", gender:"Male",
      email:"demo@smartbank.local", mobile:"9876543210", address:"12 Lake View Road", city:"Hyderabad",
      state:"Telangana", pin:"500001", username:"demo", password:"Smart@123", accountType:"Savings"
    }]);
  }
  if (!localStorage.getItem(KEYS.accounts)) {
    write(KEYS.accounts, [
      {accountNumber:"SB100001", customerId:"CUST001", type:"Savings", balance:85000, status:"Active"},
      {accountNumber:"SB100002", customerId:"CUST002", type:"Savings", balance:125000, status:"Active"}
    ]);
  }
  if (!localStorage.getItem(KEYS.transactions)) {
    write(KEYS.transactions, [
      {id:"TXN1001", accountNumber:"SB100001", type:"Credit", description:"Salary", amount:50000, date:"2026-10-01", balance:85000},
      {id:"TXN1002", accountNumber:"SB100001", type:"Debit", description:"Electricity Bill", amount:2500, date:"2026-10-02", balance:82500},
      {id:"TXN1003", accountNumber:"SB100001", type:"Credit", description:"Cash deposit", amount:5000, date:"2026-10-02", balance:87500},
      {id:"TXN1004", accountNumber:"SB100001", type:"Debit", description:"Grocery purchase", amount:2500, date:"2026-10-03", balance:85000}
    ]);
  }
};

export const formatINR = (value, compact = false) => {
  const amount = Number(value || 0);
  return new Intl.NumberFormat("en-IN", {style:"currency", currency:"INR", maximumFractionDigits: compact ? 0 : 2}).format(amount);
};

export const nextAccountNumber = () => {
  const accounts = read(KEYS.accounts, []);
  const max = accounts.reduce((highest, account) => Math.max(highest, Number(account.accountNumber.replace(/\D/g,"")) || 100000), 100000);
  return `SB${max + 1}`;
};

export const nextTransactionId = () => {
  const transactions = read(KEYS.transactions, []);
  const date = new Date();
  const prefix = `TXN${date.getFullYear()}${String(date.getMonth()+1).padStart(2,"0")}${String(date.getDate()).padStart(2,"0")}`;
  const suffix = String(transactions.length + 1).padStart(4,"0");
  return `${prefix}${suffix}`;
};
