import { User } from './User';

export class Payment {
  paymentNumber: number;
  paymentDate: Date;
  balance: number;
  principalPaid: number;
  interestPaid: number;
  accumulatedInterest: number;
  user: User;

  constructor(
    paymentNumber: number,
    paymentDate: Date,
    balance: number,
    principalPaid: number,
    interestPaid: number,
    accumulatedInterest: number,
    user: User
  ) {
    this.paymentNumber = paymentNumber;
    this.paymentDate = paymentDate;
    this.balance = balance;
    this.principalPaid = principalPaid;
    this.interestPaid = interestPaid;
    this.accumulatedInterest = accumulatedInterest;
    this.user = user;
  }

  // other methods...

  // getters and setters...
}
