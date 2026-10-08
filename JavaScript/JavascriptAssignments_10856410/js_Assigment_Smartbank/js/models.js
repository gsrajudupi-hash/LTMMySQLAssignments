
export class Customer {
  constructor({customerId, firstName, lastName, username, email = "", mobile = ""} = {}) {
    Object.assign(this, {customerId, firstName, lastName, username, email, mobile});
  }
  get fullName() { return `${this.firstName} ${this.lastName}`.trim(); }
}

export class Account {
  constructor(accountNumber, type = "Savings", balance = 0) {
    this.accountNumber = accountNumber;
    this.type = type;
    this.balance = Number(balance);
  }
  deposit(amount) { this.balance += Number(amount); return this.balance; }
  withdraw(amount) {
    amount = Number(amount);
    if (amount > 0 && amount <= this.balance) { this.balance -= amount; return true; }
    return false;
  }
}

export const sumBy = (items = [], selector = item => Number(item)) =>
  items.reduce((total, item) => total + selector(item), 0);
