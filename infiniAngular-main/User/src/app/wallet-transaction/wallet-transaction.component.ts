import { Component } from '@angular/core';
import { Transaction } from 'src/model/Transaction';
import { TransactionService } from '../Services/transaction.service';
import { Observable } from 'rxjs';
import Chart from 'chart.js/auto';
@Component({
  selector: 'app-wallet-transaction',
  templateUrl: './wallet-transaction.component.html',
  styleUrls: ['./wallet-transaction.component.css']
})
export class WalletTransactionComponent {
  errorMessage: string = '';
  transactions$: Observable<Transaction[]> | null = null;
  transactions: Transaction[] = [];
transaction: any;
  sourceRIB!: number;
  constructor(private transactionService: TransactionService) {}
  lineChart: any;
  ngOnInit(): void {
    this.getTransactionsBySourceRIB();

    this.loadCahrt();
  }

 
  getTransactionsBySourceRIB(): void {
    if (this.sourceRIB) {
      this.transactionService.getTransactionsBySourceRIB(this.sourceRIB)
        .subscribe(transactions => {
          this.transactions = transactions;
        });
    }
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
          borderColor: 'rgb(32, 191, 27)',
          tension: 0.1
        }]
      }
    });
  }
  loadCahrt(): void {
    this.transactionService.getTransactionsBySourceRIB(this.sourceRIB).subscribe((transactions: any[]) => {
      this.transactions = transactions;

      // Call a method to create the line chart
      this.createLineChart();
    });
  }
 
}
