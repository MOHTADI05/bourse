import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { BankAccount } from 'src/model/BankAccount';

import { BankAccountService } from '../Services/bank-account.service';

@Component({
  selector: 'app-update-bank-account',
  templateUrl: './update-bank-account.component.html',
  styleUrls: ['./update-bank-account.component.css']
})
export class UpdateBankAccountComponent {
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
  

}
