import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {immobilier} from "../../model/Immobilier";
import {InvestmentService} from "../Services/investment.service";
import {ImmobilierService} from "../Services/immobilier.service";

@Component({
  selector: 'app-immobilier',
  templateUrl: './credit.component.html',
  styleUrls: ['./credit.component.css']
})
export class ImmobilierComponent implements OnInit {
  immobiliers: immobilier[] = [];
  immobilierForm: FormGroup;
  editingImmobilier = false;
  amount!: number;

  constructor(private ImmobilierService:ImmobilierService ,private investmentService:InvestmentService, private fb: FormBuilder) {
    this.immobilierForm = this.fb.group({
      immobilierId: ['', Validators.required],
      description: ['', Validators.required],
      pourcentage: [null, Validators.required],
      foundingDate: [null, Validators.required],
      location: ['', Validators.required],
      name: ['', Validators.required],
      prixtotlal: [null, Validators.required],
      restprix: [null, Validators.required]
    });

  }

  ngOnInit() {
    this.loadImmobiliers();
  }

  loadImmobiliers() {
    this.ImmobilierService.getAllImmobiliers().subscribe({
      next: (data) => {
        this.immobiliers = data;
        console.log('Immobiliers loaded:', this.immobiliers);
      },
      error: (err) => {
        console.error('Error fetching immobiliers:', err);
      }
    });
  }



  addImmobilier() {
    const immobilierData: immobilier = this.immobilierForm.value;
    this.ImmobilierService.addImmobilier(immobilierData).subscribe(
      response => {
        this.immobilierForm.reset();
        this.loadImmobiliers();
        // Handle success response
        console.log('Immobilier added successfully:', response);
      },
      error => {
        // Handle error response
        console.error('Error adding immobilier:', error);
      }
    );
  }


  updateImmobilier(): void {
    const updatedImmobilier = this.immobilierForm.value as immobilier;
    this.ImmobilierService.upupdateImmobilier(updatedImmobilier).subscribe({
      next: (response) => {
        this.immobilierForm.reset();
        this.loadImmobiliers();
        // Handle successful update
      },
      error: (err) => {
      }
    });

  }



  deleteImmobilier(id: number): void {
    this.ImmobilierService.deleteImmobilier(id).subscribe({
      next: () => {
        console.log('Immobilier deleted successfully');
        // Perform any additional actions after deletion
      },
      error: (err) => {
        console.error('Failed to delete immobilier', err);
        // Handle error
      }
    });
  }

  editImmobilier(immobilier: immobilier) {
    this.immobilierForm.patchValue(immobilier);
    this.editingImmobilier = true;
  }

  cancelEdit() {
    this.editingImmobilier = false;
    this.immobilierForm.reset();
  }
  buy(imbid : number,cin:number,amount:number){
    this.investmentService.buyPercentage(imbid,cin,amount).subscribe();
  }
  sell(imbid : number,amount:number){
    this.investmentService.sellInvestment(imbid,amount).subscribe();
  }


}
