import { Component } from '@angular/core';
import {  Observable } from 'rxjs';
import { BankAccount } from 'src/model/BankAccount';
import { BankAccountService } from '../Services/bank-account.service';
import { Transaction } from 'src/model/Transaction';
import { TransactionService } from '../Services/transaction.service';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-user-wallet',
  templateUrl: './user-wallet.component.html',
  styleUrls: ['./user-wallet.component.css']
})
export class UserWalletComponent {
  bankAccount$: Observable<BankAccount> | null = null;
  bankAccounts: BankAccount[] = [];
  rib: number =1;
  errorMessage: string | null=null;
  qrCodeImage: any; // Change the type as needed
  transactions$: Observable<Transaction[]> | null = null;
  transactions: Transaction[] = [];
transaction: any;
  constructor(private bankAccountService: BankAccountService , 
    private transactionService: TransactionService) {}
   ngOnInit(): void {
    this.loadAccounts();
    this.loadTransactions(); // Load transactions when the component initializes
    this.getTransactionsBySourceRIB();

    this.loadCahrt();
  
  }
  loadTransactions(): void {
    this.transactions$ = this.transactionService.getAllTransactions();
  }
  loadAccounts(): void {
    if (this.rib) {
      this.bankAccountService.getBankAccountByRib(this.rib).subscribe(
        (data: BankAccount) => { // Change the parameter type to BankAccount
          this.bankAccounts = [data]; // Wrap the single bank account in an array
          console.log("Bank account loaded successfully: ", this.bankAccounts);
        },
        (error: any) => {
          this.errorMessage = 'Error occurred while fetching bank account: ' + error;
        }
      );
    } else {
      this.errorMessage = 'RIB number is not provided.';
    }
  }
  

  getBankAccountByRib(): void {
    if (this.rib) {
      this.bankAccount$ = this.bankAccountService.getBankAccountByRib(this.rib);
      console.log("mohtadi" + this.bankAccounts);
    }
  }
  generateQRCodeForAccount(): void {
    this.bankAccountService.generateQRCodeForAccount(this.rib)
      .subscribe(
        (qrCodeBlob) => {
          this.qrCodeImage = URL.createObjectURL(qrCodeBlob);
          this.errorMessage = ''; // Clear error message if any
        },
        (error) => {
          console.error('Error generating QR code:', error);
          this.errorMessage = 'Error occurred while generating QR code.';
          this.qrCodeImage = null; // Reset QR code image on error
        }
      );
  }
  
 
  sourceRIB!: number;
 
  lineChart: any;


 
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
