import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { BankAccount } from 'src/model/BankAccount';
import { typeAccount } from 'src/model/TypeAccount';
import { BankAccountService } from '../Services/bank-account.service';
import { FormBuilder, Validators,FormGroup } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-add-bank-account',
  templateUrl: './add-bank-account.component.html',
  styleUrls: ['./add-bank-account.component.css']
})
export class AddBankAccountComponent implements OnInit {
  [x: string]: any;
  editingBankAccount: BankAccount | null = null;  
  bankAccountForm !:FormGroup ;
  selectedType: string = '';
  BankAccounts$: Observable<BankAccount[]> | null = null;
  BankAccounts: BankAccount[] = [];
  newBankAccount: BankAccount = {
    rib: 0,
    balance: 0,
    openDate: new Date(),
    loyaltyScore: 0,
    typeAccount:0,
    user: 0,
    code: 0
  };
  errorMessage: string = '';

 

  constructor(
    private formBuilder: FormBuilder,
    private bankAccountService: BankAccountService
  ) { }

  createBankAccount(): void {
    if (this.bankAccountForm.valid) {
      const bankAccount: BankAccount = this.bankAccountForm.value;
      this.bankAccountService.createBankAccount(bankAccount)
        .subscribe(
          (createdRib) => {
            console.log('Bank account created with RIB: ', createdRib);
            // Optionally, reset the form after successful creation
            this.bankAccountForm.reset();
          },
          error => {
            this.errorMessage = 'Error occurred while creating bank account: ' + error;
          }
        );
    }
  }
  fetchBankAccounts() {
    this.bankAccountService.getAllbankAccounts()
      .subscribe(
        (accounts: BankAccount[]) => {
          this.BankAccounts = accounts;
        },
        (error: string) => {
          this.errorMessage = 'Error occurred while fetching bank accounts: ' + error;
        }
      );
  }

  ngOnInit(): void {
    this.bankAccountForm = this.formBuilder.group({
      balance: ['', Validators.required],
      openDate: ['', Validators.required],
      typeAccount: ['SAVING', Validators.required] // Assuming 'SAVING' is the default type
    });
  }
  loadAccounts(): void {
    this['BankAccountService'].getAllbankAccounts().subscribe(
      (data: BankAccount[]) => {

        this.BankAccounts = data;

        console.log("testtttttttttttttttt" +this.BankAccounts)
      },
      (error: any) => {
        this.errorMessage = 'Error occurred while fetching bank accounts: ' + error;
      }
    );
  }

 
 
}