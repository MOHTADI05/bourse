import { Component, OnInit } from '@angular/core';
import { Observable, observable } from 'rxjs';
import { BankAccount } from 'src/model/BankAccount';
import { BankAccountService } from '../Services/bank-account.service';
import { HttpErrorResponse } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import {  Chart, ChartType} from 'chart.js';
import { Transaction } from 'src/model/Transaction';
import { TransactionService } from '../Services/transaction.service';

@Component({
  selector: 'app-bank-account',
  templateUrl: './bank-account.component.html',
  styleUrls: ['./bank-account.component.css']
})
export class BankAccountComponent implements OnInit{
  [x: string]: any;
  successMessage: string = '';
  errorMessage: string = '';
  code: number = 0; // You may initialize it with a default value
  editingBankAccount: BankAccount | null = null;  
  bankAccountForm !:FormGroup ;
  BankAccounts$: Observable<BankAccount[]> | null = null;
  BankAccounts: BankAccount[] = [];
  scoreData: number[] = [];
  chart: Chart|null = null;
  transactions$: Observable<Transaction[]> | null = null;
  transactions: Transaction[] = [];
   rib: any;
   lineChart: any;
  totalCashFlow: number=0;
  numberOfMonths: number=0;
  
  constructor(private BankAccountService: BankAccountService) {}

  // bankAccounts: BankAccount[] = [];
  

 calculateTotalCashFlow(): void {
    this.BankAccountService.calculateTotalCashFlow(this.numberOfMonths)
      .subscribe(
        (cashFlow) => {
          this.totalCashFlow = cashFlow;
          this.errorMessage = ''; // Clear error message if any
        },
        (error) => {
          console.error('Error calculating total cash flow:', error);
          this.errorMessage = 'Error occurred while calculating total cash flow.';
          this.totalCashFlow = 0; // Reset total cash flow on error
        }
      );
  }


  ngOnInit(): void {
    this.loadAccounts();
    this.calculateScore(this.rib);
    this.loadCahrt();
  }

  loadAccounts(): void {
    this.BankAccountService.getAllbankAccounts().subscribe(
      (data: BankAccount[]) => {

        this.BankAccounts = data;

        console.log("testtttttttttttttttt" +this.BankAccounts)
      },
      (error: any) => {
        this.errorMessage = 'Error occurred while fetching bank accounts: ' + error;
      }
    );
  }
  
  deleteAccount(rib: number): void {
    this.BankAccountService.deleteBankAccountById(rib)
      .subscribe(
        () => {
          this.loadAccounts();
        },
        error => {
          this.errorMessage = 'Erreur lors de la suppression de la transaction : ' + error;
        }
      );
  }
  
  updateAccount(): void {
    if (this.editingBankAccount && this.bankAccountForm.valid) {
      const updatedAccount: BankAccount = this.bankAccountForm.value as BankAccount;
      this.BankAccountService.saveOrUpdateBankAccount(updatedAccount).subscribe(
        () => {
          this.loadAccounts(); // Reload accounts after update
          this.bankAccountForm.reset(); // Reset the form after update
          this.editingBankAccount = null; // Clear the editing state
        },
        (error) => {
          console.error('Error updating bank account:', error);
          // Handle error if needed
        }
      );
    }
  }
  

 

  getBankAccountByRib(rib: number): void {
    this.BankAccountService.getBankAccountByRib(rib)
      .subscribe(
        (bankAccount) => {
          // Do something with the retrieved bank account
        },
        error => {
          this.errorMessage = 'Error occurred while fetching bank account: ' + error;
        }
      );
  }

  generateQRCodeForAccount(rib: number): void {
    this.BankAccountService.generateQRCodeForAccount(rib)
      .subscribe(
        (qrCodeImage) => {
          // Do something with the generated QR code image
        },
        error => {
          this.errorMessage = 'Error occurred while generating QR code: ' + error;
        }
      );
  }

  
  addBonus(): void {
    this.BankAccountService.addBonusToAccount(this.code)
      .subscribe(
        (message) => {
          this.successMessage = message;
          this.errorMessage = ''; // Clear error message if any
        },
        (error) => {
          console.error('Error occurred while adding bonus:', error);
          this.errorMessage = error;
          this.successMessage = ''; // Clear success message if any
        }
      );
  }
  
  createBankAccount(bankAccount: BankAccount): void {
    this.BankAccountService.saveOrUpdateBankAccount(bankAccount)
      .subscribe(
        (createdRib) => {
          console.log('Bank account created with RIB: ', createdRib);
        },
        error => {
          this.errorMessage = 'Error occurred while creating bank account: ' + error;
        }
      );
  }
  scoreMessage: string = '';

  calculateScore(rib: number): void {
    this.BankAccountService.calculateScore(rib)
      .subscribe(
        (message: string) => {
          console.log(`Score calculation for account ID ${rib}: ${message}`);
          const scores = this.parseScoreMessage(message);
          console.log('Parsed scores:', scores);
          this.scoreData = [scores.balanceScore, scores.loyaltyScore, scores.transactionScore, scores.riskScore];
          // Now you have the scores, you can use them to render your chart
          this.renderChart();
        },
        (error: HttpErrorResponse) => {
          console.error('Error calculating score:', error.error);
          // Handle the error appropriately
        }
      );
  }

  parseScoreMessage(message: string): { balanceScore: number, loyaltyScore: number, transactionScore: number, riskScore: number } {
    const regex = /The balance score is (\d+)\. The Loyalty Score is (\d+)\. The transaction score is (\d+)\. The risk score is (\d+)/;
    const match = message.match(regex);
    if (match) {
      return {
        balanceScore: parseInt(match[1]),
        loyaltyScore: parseInt(match[2]),
        transactionScore: parseInt(match[3]),
        riskScore: parseInt(match[4])
      };
    } else {
      // Handle the case where the message format doesn't match
      console.error('Invalid score message format:', message);
      return { balanceScore: 0, loyaltyScore: 0, transactionScore: 0, riskScore: 0 };
    }
  }

  renderChart(): void {
    const ctx = document.getElementById('polar-area-chart') as HTMLCanvasElement;
    this.chart = new Chart(ctx, {
      type: 'polarArea' as ChartType,
      data: {
        labels: ['Balance Score', 'Loyalty Score', 'Transaction Score', 'Risk Score'],
        datasets: [{
          label: 'Scores',
          data: this.scoreData,
          backgroundColor: [
            'rgba(255, 99, 132, 0.5)',
            'rgba(54, 162, 235, 0.5)',
            'rgba(255, 206, 86, 0.5)',
            'rgba(75, 192, 192, 0.5)'
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          r: {
            suggestedMin: 0,
            suggestedMax: 10
          }
        },
        animation: false
      }
    });
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
    this['transactionService'].getAllTransactions().subscribe((transactions: any[]) => {
      this.transactions = transactions;

      // Call a method to create the line chart
      this.createLineChart();
    });
  }
}