import { Component } from '@angular/core';
import { Transaction } from 'src/model/Transaction';
import { TransactionService } from '../Services/transaction.service';
import { Observable } from 'rxjs';
import Chart from 'chart.js/auto';


@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent {
  errorMessage: string = '';
  transactions$: Observable<Transaction[]> | null = null;
  transactions: Transaction[] = [];
  constructor(private transactionService: TransactionService) {}
  lineChart: any;
  ngOnInit(): void {
    this.loadTransactions();
    this.loadCahrt();
  }

  createLineChart(): void {
    const ctx = document.getElementById('lineChart') as HTMLCanvasElement;
    this.lineChart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: this.transactions.map(transaction => transaction.transactionDate), // Assuming you have a date property in each transaction object
        datasets: [{
          label: 'Transaction Amount',
          data: this.transactions.map(transaction => transaction.amount), // Assuming you have an amount property in each transaction object
          fill: false,
          borderColor: 'rgb(75, 192, 192)',
          tension: 0.1
        }]
      }
    });
  }
  loadCahrt(): void {
    this.transactionService.getAllTransactions().subscribe((transactions: any[]) => {
      this.transactions = transactions;

      // Call a method to create the line chart
      this.createLineChart();
    });
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
