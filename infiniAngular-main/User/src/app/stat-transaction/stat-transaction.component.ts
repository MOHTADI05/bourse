import { Component } from '@angular/core';
import { Transaction } from 'src/model/Transaction';
import { TransactionService } from '../Services/transaction.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-stat-transaction',
  templateUrl: './stat-transaction.component.html',
  styleUrls: ['./stat-transaction.component.css']
})
export class StatTransactionComponent {
getLineChartData(_t7: Transaction[]) {
throw new Error('Method not implemented.');
}
  errorMessage: string = '';
  transactions$: Observable<Transaction[]> | null = null;
  transactions: Transaction[] = [];
colorScheme: any;
  constructor(private transactionService: TransactionService) {}

  ngOnInit(): void {
    this.loadTransactions();
  }

  getChartData(transactions: Transaction[]): any[] {
    // Convert transaction data into chart data format
    const chartData = transactions.map(transaction => {
      return {
        name: transaction.transactionDate,
        value: transaction.amount
      };
    });
    return chartData;
  }

  loadTransactions(): void {
    this.transactions$ = this.transactionService.getAllTransactions();
  }

  saveTransaction(transaction: Transaction): void {
    this.transactionService.saveOrUpdateTransaction(transaction)
      .subscribe(
        () => {
          this.loadTransactions();
        },
        error => {
          this.errorMessage = 'Erreur lors de l\'enregistrement de la transaction : ' + error;
        }
      );
  }

  deleteTransaction(transactionId: number): void {
    this.transactionService.deleteTransactionById(transactionId)
      .subscribe(
        () => {
          this.loadTransactions();
        },
        error => {
          this.errorMessage = 'Erreur lors de la suppression de la transaction : ' + error;
        }
      );
  }

  getTransaction(transactionId: number): void {
    this.transactionService.getTransactionById(transactionId)
      .subscribe(
        (transaction) => {
          console.log('Transaction récupérée :', transaction);
        },
        error => {
          this.errorMessage = 'Erreur lors de la récupération de la transaction : ' + error;
        }
      );
  }

  transferMoney(sourceAccountId: number, destinationAccountId: number, amount: number): void {
    this.transactionService.transferMoney(sourceAccountId, destinationAccountId, amount)
      .subscribe(
        (message) => {
          console.log('Transfert réussi :', message);
        },
        error => {
          this.errorMessage = 'Erreur lors du transfert d\'argent : ' + error;
        }
      );
  }

  depositMoney(destinationAccountId: number, amount: number, mail: string): void {
    this.transactionService.depositMoney(destinationAccountId, amount, mail)
      .subscribe(
        (message) => {
          console.log('Dépôt réussi :', message);
        },
        error => {
          this.errorMessage = 'Erreur lors du dépôt d\'argent : ' + error;
        }
      );
  }

  withdrawMoney(sourceAccountId: number, amount: number, mail: string): void {
    this.transactionService.withdrawMoney(sourceAccountId, amount, mail)
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
