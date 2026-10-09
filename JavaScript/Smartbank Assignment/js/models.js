// ES6 classes
export class Account {
  constructor(accountNumber, customerId, type, balance = 0, status = "Active") {
    Object.assign(this, { accountNumber, customerId, type, balance, status });
  }
  deposit(amount) {
    if (!(amount > 0)) throw new RangeError("Amount must be greater than zero");
    this.balance += amount;
    return this.balance;
  }
  withdraw(amount) {
    if (!(amount > 0)) throw new RangeError("Amount must be greater than zero");
    if (amount > this.balance) throw new Error("Insufficient balance");
    this.balance -= amount;
    return this.balance;
  }
  static from({ accountNumber, customerId, type, balance, status }) {
    return new Account(accountNumber, customerId, type, balance, status);
  }
}
export class Customer {
  constructor(data) {
    Object.assign(this, data);
  }
  get fullName() {
    return `${this.firstName} ${this.lastName}`;
  }
}
