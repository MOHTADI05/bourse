import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {ServiceuserService} from "../Services/serviceuser.service";
import {Demand} from "../../model/demande";
import {InvHistory} from "../../model/InvHistory";
import {User} from "../../model/User";
import {immobilier} from "../../model/Immobilier";
import {InvestmentService} from "../Services/investment.service";
import {ImmobilierService} from "../Services/immobilier.service";
import {DemandeService} from "../Services/demandeservice.service";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../Services/auth-service.service";

@Component({
  selector: 'app-sell',
  templateUrl: 'sell-to-demand.component.html',
  styleUrls: ['sell-to-demand.component.css']
})
export class SellToDemandComponent implements OnInit {
  demands: Demand[] = [];
  demandForm: FormGroup;
  users: User[] = []; // Assuming you have a list of users
  immobiliers: immobilier[] = [];
  immobilierId!: number;



  constructor(    private route: ActivatedRoute,
                  private investmentService: InvestmentService,private immobilierservice : ImmobilierService,private serviceuserService:ServiceuserService,private fb: FormBuilder,private demandeService:DemandeService,private authService:AuthService) {
    this.demandForm = this.fb.group({
      id: ['', Validators.required],
      user: ['', Validators.required], // Assuming you have a form control for selecting user
      immobilier: ['', Validators.required], // Assuming you have a form control for selecting immobilier
      amountRequested: ['', Validators.required]
    });

  }

  ngOnInit(): void {
    this.immobilierservice.getAllImmobiliers().subscribe(immobiliers => this.immobiliers = immobiliers);
    this.serviceuserService.findAlluser().subscribe(users => this.users = users);

    this.route.queryParams.subscribe(params => {
      this.immobilierId = +params['immobilierId'];
      this.fetchDemands(this.immobilierId);
    });


  }
  loadDemande() {
    this.demandeService.getAllDemands().subscribe({
      next: (data) => {
        this.demands = data;
        console.log('Immobiliers loaded:', this.immobiliers);
      },
      error: (err) => {
        console.error('Error fetching immobiliers:', err);
      }
    });
  }

  fetchDemands(immobilierId: number): void {

    this.demandeService.getDemandsForImmobilier(immobilierId).subscribe({
      next: (demands) => this.demands = demands,

      error: (error) => console.error('Failed to fetch demands:', error)

    })
    ;

  }



  sellToDemand(demandId: number): void {
    const token = localStorage.getItem('jwt');

    if (token) {
      this.authService.getUserId(token).subscribe(
        id => {
          console.log('ID utilisateur récupéré:', id);
          // Now that you have the id (cin), you can proceed with the sell operation
          this.investmentService.sellPercentageToDemand(demandId, id).subscribe(
            (historyList: InvHistory[]) => {
              console.log('Sold percentage to demand successfully!', historyList);
              // Handle success scenario if needed
            },
            (error: any) => {
              console.error('Error selling percentage to demand:', error);
              // Handle error scenario if needed
            }
          );
        },
        error => {
          console.error('Erreur lors de la récupération de l\'ID utilisateur:', error);
          // Handle the error here as needed
        }
      );
    } else {
      console.error('Aucun token JWT trouvé dans le localStorage');
    }
  }




  protected readonly Number = Number;
}