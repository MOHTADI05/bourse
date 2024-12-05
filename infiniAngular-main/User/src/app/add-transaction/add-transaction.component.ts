import { Component } from '@angular/core';
import { Transaction } from 'src/model/Transaction';
import { TransactionService } from '../Services/transaction.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-add-transaction',
  templateUrl: './add-transaction.component.html',
  styleUrls: ['./add-transaction.component.css']
})
export class AddTransactionComponent {
  errorMessage: string = '';
  transactions$: Observable<Transaction[]> | null = null;
  transactions: Transaction[] = [];
  
  sourceAccountId: number = 0;
  destinationAccountId: number = 0;
  amount: number = 0;
  mail: string = '';


  // Transfer Money Form
  sourceAccountIdTransfer: number = 0;
  destinationAccountIdTransfer: number = 0;
  amountTransfer: number = 0;
  
  // Deposit Money Form
  destinationAccountIdDeposit: number = 0;
  amountDeposit: number = 0;
  mailDeposit: string = '';

  // Withdraw Money Form
  sourceAccountIdWithdraw: number = 0;
  amountWithdraw: number = 0;
  mailWithdraw: string = '';

  constructor(private transactionService: TransactionService) {}

  transferMoney(): void {
      this.transactionService.transferMoney(this.sourceAccountIdTransfer, this.destinationAccountIdTransfer, this.amountTransfer)
          .subscribe(
              (message) => {
                  console.log('Transfert réussi :', message);
              },
              error => {
                  this.errorMessage = 'Erreur lors du transfert d\'argent : ' + error;
              }
          );
  }

  depositMoney(): void {
      this.transactionService.depositMoney(this.destinationAccountIdDeposit, this.amountDeposit, this.mailDeposit)
          .subscribe(
              (message) => {
                  console.log('Dépôt réussi :', message);
              },
              error => {
                  this.errorMessage = 'Erreur lors du dépôt d\'argent : ' + error;
              }
          );
  }

  withdrawMoney(): void {
    this.transactionService.withdrawMoney(this.sourceAccountIdWithdraw, this.amountWithdraw, this.mailWithdraw)
      .subscribe(
        (message) => {
          console.log('Retrait réussi :', message);
        },
        error => {
          this.errorMessage = 'Erreur lors du retrait d\'argent : ' + error;
        }
      );
  }
  
}
