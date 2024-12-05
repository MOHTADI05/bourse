 
import { User } from './User';

import { Transaction } from './Transaction';
import { typeAccount } from './TypeAccount';
 
export class BankAccount {
    rib: number;
    balance: number;
    openDate: Date;
    code: number;
    loyaltyScore: number;
    typeAccount: typeAccount;
    user: number;
    
   
    constructor() {
        this.rib = 0;
        this.balance = 0;
        this.openDate = new Date();
        this.code = 0;
        this.loyaltyScore = 0;
        this.typeAccount = typeAccount.CURRENT;
        this.user = 0;
    }

    
    }