import { Component } from '@angular/core';

import { Observable, observable } from 'rxjs';
import { BankAccount } from 'src/model/BankAccount';
import { BankAccountService } from '../Services/bank-account.service';
import { HttpErrorResponse } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
@Component({
  selector: 'app-bank-account-bonus',
  templateUrl: './bank-account-bonus.component.html',
  styleUrls: ['./bank-account-bonus.component.css']
})
export class BankAccountBonusComponent {

  code1!: string;
  code2!: string;
  code3!: string;
  

  


  successMessage: string = '';
  errorMessage: string = '';
  code: number = 0; // You may initialize it with a default value
  editingBankAccount: BankAccount | null = null;  
  bankAccountForm !:FormGroup ;
  BankAccounts$: Observable<BankAccount[]> | null = null;
  BankAccounts: BankAccount[] = [];
rib: any;
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
    const codeString = this.code1 + this.code2 + this.code3;
    const codeNumber = parseInt(codeString, 10);
  
    this.BankAccountService.addBonusToAccount(codeNumber)
      .subscribe(
        (message) => {
          this.successMessage = message;
          this.errorMessage = ''; 
        },
        (error) => {
          console.error('Error occurred while adding bonus:', error);
          this.errorMessage = 'Error occurred while adding bonus.';
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
      (message) => {
        console.log(`Score calculation for account ID ${rib}: ${message}`);
        this.scoreMessage = message; // Store the message
      },
      (error: HttpErrorResponse) => {
        console.error('Error calculating score:', error.error);
        // Handle the error appropriately
      }
    );
}

}

