
import { read, write, KEYS, currentCustomer, customerAccounts, nextTransactionId } from "./storage.js";
import { Account } from "./models.js";
// Transaction service for handling deposits and withdrawals
export const deposit = (accountNumber, amount, description = "Cash deposit") => {
  const accounts = read(KEYS.accounts, []);
  const accountData = accounts.find(account => account.accountNumber === accountNumber);
  if (!accountData || Number(amount) <= 0) throw new Error("Invalid deposit");
  const account = new Account(accountData.accountNumber, accountData.type, accountData.balance);
  account.deposit(amount);
  accountData.balance = account.balance;
  write(KEYS.accounts, accounts);
  const transactions = read(KEYS.transactions, []);
  transactions.push({id: nextTransactionId(), accountNumber, type:"Credit", description, amount:Number(amount), date:new Date().toISOString().slice(0,10), balance:account.balance});
  write(KEYS.transactions, transactions);
  return accountData;
};
// Withdrawal service for handling cash withdrawals
export const withdraw = (accountNumber, amount, description = "Cash withdrawal") => {
  const accounts = read(KEYS.accounts, []);
  const accountData = accounts.find(account => account.accountNumber === accountNumber);
  if (!accountData) throw new Error("Account not found");
  const account = new Account(accountData.accountNumber, accountData.type, accountData.balance);
  if (!account.withdraw(amount)) throw new Error("Insufficient balance");
  accountData.balance = account.balance;
  write(KEYS.accounts, accounts);
  const transactions = read(KEYS.transactions, []);
  transactions.push({id: nextTransactionId(), accountNumber, type:"Debit", description, amount:Number(amount), date:new Date().toISOString().slice(0,10), balance:account.balance});
  write(KEYS.transactions, transactions);
  return accountData;
};
