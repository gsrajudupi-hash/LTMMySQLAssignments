export class Customer {
  constructor({
    customerId, firstName, lastName, dob, gender, email, mobile, address,
    city, state, pin, username, password, accountNumber = ""
  }) {
    Object.assign(this, {
      customerId, firstName, lastName, dob, gender, email, mobile, address,
      city, state, pin, username, password, accountNumber
    });
  }

  get fullName() {
    return `${this.firstName} ${this.lastName}`.trim();
  }
}
