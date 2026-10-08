import { getTransactions, saveTransactions } from "./storage.js";

export const generateTransactionId = () => {
  const now = new Date();
  const datePart = now.toISOString().slice(0, 10).replaceAll("-", "");
  const count = getTransactions().length + 1;
  return `TXN${datePart}${String(count).padStart(4, "0")}`;
};

export const createTransaction = (
  accountNumber,
  type,
  amount,
  description = "Banking transaction",
  ...extra
) => {
  const transactions = getTransactions();
  const transaction = {
    id: generateTransactionId(),
    accountNumber,
    type,
    description,
    amount: Number(amount),
    date: new Date().toISOString().slice(0, 10),
    balance: 0,
    ...extra
  };
  transactions.push(transaction);
  saveTransactions(transactions);
  return transaction;
};

export const getAccountTransactions = (accountNumber) =>
  getTransactions().filter(({ accountNumber: number }) => number === accountNumber);

export const updateTransactionBalance = (transactionId, balance) => {
  const transactions = getTransactions();
  const transaction = transactions.find(item => item.id === transactionId);
  if (transaction) transaction.balance = Number(balance);
  saveTransactions([...transactions]);
};

export const transactionStats = (transactions = []) => {
  const amounts = transactions.map(({ amount }) => Number(amount));
  const total = transactions.reduce((sum, { amount }) => sum + Number(amount), 0);
  const credits = transactions.filter(({ type }) => type === "Credit")
    .reduce((sum, { amount }) => sum + Number(amount), 0);
  const debits = transactions.filter(({ type }) => type === "Debit")
    .reduce((sum, { amount }) => sum + Number(amount), 0);
  return {
    total,
    credits,
    debits,
    average: amounts.length ? total / amounts.length : 0,
    highest: amounts.length ? Math.max(...amounts) : 0,
    lowest: amounts.length ? Math.min(...amounts) : 0
  };
};
