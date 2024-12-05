import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {immobilier} from "../../model/Immobilier";
import {InvestmentService} from "../Services/investment.service";
import {ImmobilierService} from "../Services/immobilier.service";
import {Router} from "@angular/router";
import {DemandeService} from "../Services/demandeservice.service";
import {Demand} from "../../model/demande";

@Component({
  selector: 'app-immobilier',
  templateUrl: './credit.component.html',
  styleUrls: ['./credit.component.css']
})
export class ImmobilierFontComponent implements OnInit {
  immobiliers: immobilier[] = [];
  immobilierForm: FormGroup;
  editingImmobilier = false;
  Demands: Demand[] = [];
  user?: { cin: number; role: string; }; // Note the '?', indicating user may be undefined

  constructor(private ImmobilierService:ImmobilierService ,private investmentService:InvestmentService, private fb: FormBuilder,private router: Router,private demandeService:DemandeService) {
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


  pass(data:immobilier){
    const jsonData = JSON.stringify(data);

    sessionStorage.setItem("immobilier", jsonData)
    this.router.navigate(['/front/loan-simulation']);  }
  navigateToSellToDemand(data:immobilier) {
    const jsonData = JSON.stringify(data);

    sessionStorage.setItem("immobilier", jsonData)
    // Any additional logic

  }
// In your credit.component.ts

  pass1(data:immobilier){
    const jsonData = JSON.stringify(data);

    sessionStorage.setItem("immobilier", jsonData)
    this.router.navigate(['/front/app-demandesimulateur']);  }



  navigateToSellToDemand2(immobilierId: number): void {
    this.demandeService.getDemandsForImmobilier(immobilierId).subscribe({
      next: (demands) => {
        this.Demands = demands;
        console.log('Demands fetched:', demands);
        console.log('Fetching demands for immobilier ID:', immobilierId);
        this.router.navigate(['/front/sell-to-demand'], {queryParams: {immobilierId: immobilierId}});


      },
      error: (error) => console.error('Error fetching demands:', error)
    });
  }

  getImageForCredit(immobilierId: number): string {
    // Mappez chaque pack à son image correspondante
    switch (immobilierId) {
      case 1:
        return "assets/images/immobilier/AdobeStock_294545982-1024x683.jpg";
      case 2:
        return "assets/images/immobilier/images (2).jpg";
      case 4:
        return "assets/images/immobilier/images (1).jpg";
      case 52:
        return "assets/images/immobilier/téléchargement.jpg";
      case 103:
        return "assets/images/immobilier/téléchargement (1).jpg";
      case 104:
        return "assets/images/immobilier/téléchargement (2).jpg";
      case 105:
        return "assets/images/immobilier/signature-offre-pret_1.jpg";
      // Ajoutez d'autres cas pour les autres packs si nécessaire
      default:
        return ""; // Retourne une chaîne vide par défaut si aucune correspondance trouvée
    }
  }

}
