import { Component } from '@angular/core';
import {ImmobilierService} from "../Services/immobilier.service";
import {ActivatedRoute} from "@angular/router";
import {InvestmentService} from "../Services/investment.service";
import {AuthService} from "../Services/auth-service.service";
import {immobilier} from "../../model/Immobilier";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-demandesimulateur',
  templateUrl: './demandesimulateur.component.html',
  styleUrls: ['./demandesimulateur.component.css']
})
export class DemandesimulateurComponent {
  property: any = {};
  loanAmount: number = 0;
  Amount!: number ;
  prixtotal!: number ;
  id!:number;
  percentage:number=0;
  x!:() => any;

  duration: number = 1;
  monthlyPayment: number = 0;
  immobilierData: any;

  constructor(private propertyService: ImmobilierService, private route: ActivatedRoute,private investmentService:InvestmentService,private  authService : AuthService,  private toastr: ToastrService) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = +params['id']; // Convert string 'id' to a number using the unary '+' operator
      if (id) {
        this.propertyService.getImmobilierById(id).subscribe(property => {
          console.log("Property Data:", this.property);
          console.log("Rest Prix:", this.property.restPrix);
          this.property = property;
          this.loanAmount = property.restPrix ; // Assign restPrix or default value
          this.calculateMonthlyPayment();
        });
      }
    });

    var immobilierString = sessionStorage.getItem("immobilier");

    // @ts-ignore
    var immobilierObject = JSON.parse(immobilierString);

    var restprixValue = immobilierObject.restprix;
    this.id=immobilierObject.immobilierId;
    this.Amount=restprixValue;
    console.log((immobilierObject))
    this.prixtotal = immobilierObject.prixtotlal;

    console.log("restprix : " , restprixValue , " prix total : ", this.prixtotal);

  }


  submitLoan(): void {
    console.log('Loan submitted:', this.loanAmount, this.duration);
    // Additional submission logic here
  }
  calculateMonthlyPayment(): void {
    if (this.property && this.duration) {
      const interestRate = 0.05 / 12; // Monthly interest rate
      this.monthlyPayment = (this.loanAmount * interestRate) / (1 - Math.pow(1 + interestRate, -this.duration));
    }
  }

  onDurationChange(newDuration: number): void {
    this.duration = newDuration;
    this.calculateMonthlyPayment();
  }
  buy(imbid: number, amount: number): void {
    const token = localStorage.getItem('jwt');

    if (token) {
      this.authService.getUserId(token).subscribe(
        id => {
          console.log('ID utilisateur récupéré:', id);
          // Now that you have the id (cin), you can proceed with the buy operation
          this.investmentService.buyPercentage(imbid, id, amount).subscribe(
            () => {
              // Success callback
              console.log("Buy successful");
              this.toastr.success("Demand Accepted");
            },
            (error) => {
              // Error callback
              console.error("An error occurred while buying percentage:", error);
              this.toastr.error("Demand Rejected");
              // You can handle the error here, for example, displaying a message to the user
              // or performing any other action you deem necessary.
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

  protected readonly immobilier = immobilier;

  calculate(){

    console.log(this.loanAmount/this.prixtotal*100);
    this.percentage = (this.loanAmount/this.prixtotal)*100;
  }

  function () : void {
    const token = localStorage.getItem('jwt');

    if (token) {
      this.authService.getUserId(token).subscribe(
        id => {
          console.log('ID utilisateur récupéré:', id);

        },
        error => {
          console.error('Erreur lors de la récupération de l\'ID utilisateur:', error);
          // Gérez l'erreur ici selon vos besoins
        }
      );
    } else {
      console.error('Aucun token JWT trouvé dans le localStorage');
    }
  }
}