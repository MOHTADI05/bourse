import { Component, OnInit } from '@angular/core';

import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Observable} from "rxjs";
import {Investisment} from "../../model/investisment";
import {InvestmentService} from "../Services/investment.service";

@Component({
  selector: 'app-investment-management',
  templateUrl: './investment-management.component.html',
  styleUrls: ['./investment-management.component.css']
})
export class InvestmentManagementComponent implements	OnInit {
  Investments: Investisment[] = [];
  InvestismentForm: FormGroup;
  constructor(private investmentService: InvestmentService, private fb: FormBuilder) {
    this.InvestismentForm = this.fb.group({
      inv_id: ['', Validators.required],
      amount: ['', Validators.required],
      inv_date: [null, Validators.required],
      inv_owner: ['', Validators.required],
      imb: ['', Validators.required],


    });
  }

  ngOnInit() {
    this.loadInvestments();
  }

  loadInvestments() {
    this.investmentService.getAllInvestisment().subscribe(
      (data: any[]) => {
        this.Investments = data;
        // @ts-ignore
        console.log('Investments loaded:', this.Investments);
      },
      error => {
        console.error('Error loading investments:', error);
      }
    );
  }

  buyPercentage( userId: number,imbId: number, amount: number) {
    this.investmentService.buyPercentage(imbId, userId, amount).subscribe({
      next: (invHistory) => {
        console.log('Buy operation successful', invHistory);
      },
      error: (error) => {
        console.error('Buy operation failed', error);
        // Handle errors here, like showing a message to the user
      }
    });
  }




}

