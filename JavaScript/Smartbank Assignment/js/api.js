// Promises, async/await and Fetch API
import { store } from "./storage.js";

export const loadDataThen = () =>
  // Fetch with .then()
  fetch("./data/customers.json").then((r) => {
    if (!r.ok) throw new Error(`HTTP ${r.status}`);
    return r.json();
  });

export async function loadDataAsync() {
  // same thing with async/await
  const res = await fetch("./data/customers.json");
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}
export const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

export const checkAccount = (accountNumber, accounts = store.get("accounts")) =>
  new Promise((resolve, reject) =>
    setTimeout(() => {
      const acc = accounts.find((a) => a.accountNumber === accountNumber);
      acc ? resolve(acc) : reject(new Error("Account not found"));
    }, 800),
  );

const FALLBACK = {
  // used only if the JSON file cannot be fetched
  customers: [
    {
      customerId: "CUST001",
      firstName: "Srikanth",
      lastName: "Mareedu",
      dob: "1995-05-13",
      gender: "Male",
      email: "srikanth@example.com",
      mobile: "9876543210",
      address: "12 SD Road",
      city: "Hyderabad",
      state: "Telangana",
      pin: "500003",
      username: "srikanth",
      password: "Nani@1234",
    },
  ],
  accounts: [
    {
      accountNumber: "SB100001",
      customerId: "CUST001",
      type: "Savings",
      balance: 85000,
      status: "Active",
    },
  ],
  transactions: [
    {
      id: "TXN1001",
      accountNumber: "SB100001",
      type: "Credit",
      category: "Deposit",
      description: "Salary",
      amount: 50000,
      date: "2026-10-01",
      balance: 85000,
    },
  ],
};
export async function seedData() {
  if (localStorage.getItem("seeded")) return;
  let data;
  try {
    data = await loadDataAsync();
  } catch (err) {
    console.warn("Using fallback data:", err.message);
    data = FALLBACK;
  }
  Object.entries(data).forEach(([key, value]) => store.set(key, value));
  localStorage.setItem("seeded", "1");
}
