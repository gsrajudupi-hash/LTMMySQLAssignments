export const StorageManager = {
  saveCustomers(customers) {
    localStorage.setItem("customers", JSON.stringify(customers));
  },
  getCustomers() {
    return JSON.parse(localStorage.getItem("customers")) || [];
  },
  setLoggedInUser(username) {
    sessionStorage.setItem("loggedInUser", username);
  },
  getLoggedInUser() {
    return sessionStorage.getItem("loggedInUser");
  },
  clearSession() {
    sessionStorage.removeItem("loggedInUser");
  }
};