import { BankAccount } from './BankAccount';


export class Transaction {
    transactionId: number;
    amount?: number;
    transactionDate?: Date;
    destination?: number;
    sourceRIB?: BankAccount;

    constructor() {
        this.transactionId = 0;
        this.amount = 0;
        this.transactionDate = new Date();
        this.destination = 0;
        this.sourceRIB = new BankAccount();
    }
}
