import { Payment } from './Payment';

export class Amortization {
  amortizationNumber: number;
  startDate: Date;
  initialBalance: number;
  interestRate: number;
  durationInMonths: number;
  futureValue: number;
  paymentType: number;
  monthlyPayment: number;
  paymentList: Payment[];

  constructor(
    amortizationNumber: number,
    startDate: Date,
    initialBalance: number,
    interestRate: number,
    durationInMonths: number,
    futureValue: number,
    paymentType: number,
    monthlyPayment: number,
    paymentList: Payment[]
  ) {
    this.amortizationNumber = amortizationNumber;
    this.startDate = startDate;
    this.initialBalance = initialBalance;
    this.interestRate = interestRate;
    this.durationInMonths = durationInMonths;
    this.futureValue = futureValue;
    this.paymentType = paymentType;
    this.monthlyPayment = monthlyPayment;
    this.paymentList = paymentList;
  }

  addAllPayments(paymentList: Payment[]): void {
    this.paymentList.push(...paymentList);
  }

  toString(): string {
    return `[
      startDate: ${this.startDate},
      initialBalance: ${this.initialBalance},
      interestRate: ${this.interestRate},
      durationInMonths: ${this.durationInMonths},
      futureValue: ${this.futureValue},
      paymentType: ${this.paymentType},
      monthlyPayment: ${this.monthlyPayment}
    ]`;
  }
}
