export class Account {
  constructor(accountNumber, type, balance = 0) {
    this.accountNumber = accountNumber;
    this.type = type;
    this.balance = balance;
  }
 
  deposit(amount) {
    if (amount > 0) {
      this.balance += amount;
      return true;
    }
    return false;
  }
 
  withdraw(amount) {
    if (amount > 0 && amount <= this.balance) {
      this.balance -= amount;
      return true;
    }
    return false;
  }
}