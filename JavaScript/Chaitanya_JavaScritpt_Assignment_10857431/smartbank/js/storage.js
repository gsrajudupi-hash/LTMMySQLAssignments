const getJSON = (key, fallback = []) => {
  try { return JSON.parse(localStorage.getItem(key)) || fallback; }
  catch (error) { return fallback; }
};

export const getCustomers = () => getJSON("customers", []);
export const saveCustomers = customers => localStorage.setItem("customers", JSON.stringify(customers));
export const getAccounts = () => getJSON("accounts", []);
export const saveAccounts = accounts => localStorage.setItem("accounts", JSON.stringify(accounts));
export const getTransactions = () => getJSON("transactions", []);
export const saveTransactions = transactions => localStorage.setItem("transactions", JSON.stringify(transactions));
export const getBeneficiaries = () => getJSON("beneficiaries", []);
export const saveBeneficiaries = beneficiaries => localStorage.setItem("beneficiaries", JSON.stringify(beneficiaries));

export const getLoggedInUser = () => sessionStorage.getItem("loggedInUser");
export const setLoggedInUser = username => sessionStorage.setItem("loggedInUser", username);
export const logout = () => sessionStorage.removeItem("loggedInUser");

export const currentCustomer = () => getCustomers().find(customer => customer.username === getLoggedInUser());

export const showMessage = (text, type = "success", target = "message") => {
  const box = document.getElementById(target);
  if (box) box.innerHTML = `<div class="message ${type}">${text}</div>`;
};

export const money = amount => `₹${Number(amount || 0).toLocaleString("en-IN", {minimumFractionDigits:2})}`;
