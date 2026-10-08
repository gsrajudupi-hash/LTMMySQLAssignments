export class Account {
  constructor(accountNumber, type, balance = 0, customerId = "", status = "Active") {
    this.accountNumber = accountNumber;
    this.type = type;
    this.balance = Number(balance);
    this.customerId = customerId;
    this.status = status;
  }

  deposit(amount) {
    this.balance += Number(amount);
  }

  withdraw(amount) {
    if (Number(amount) <= this.balance) {
      this.balance -= Number(amount);
      return true;
    }
    return false;
  }
}

export const generateAccountNumber = (accounts = []) => {
  const next = accounts.length + 100001;
  return `SB${String(next).padStart(6, "0")}`;
};
