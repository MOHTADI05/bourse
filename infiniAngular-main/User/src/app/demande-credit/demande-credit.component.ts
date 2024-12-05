import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder,FormGroup,ValidationErrors,Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { DemandeCreditService } from '../Services/demande-credit.service';
import { Router } from '@angular/router';
import { GarantorService } from '../Services/garantor.service';
import { DemandeCredit } from 'src/model/DemandeCredit';
import { Garantor } from 'src/model/Garantor';
import { CreditService } from '../Services/credit.service';
import { Credit } from 'src/model/Credit';
import { ToastrService } from 'ngx-toastr';
import { MatDialog } from '@angular/material/dialog';
import { PopUpCreditComponent } from '../pop-up-credit/pop-up-credit.component';
import { AuthService } from '../Services/auth-service.service';

@Component({
  selector: 'app-demande-credit',
  templateUrl: './demande-credit.component.html',
  styleUrls: ['./demande-credit.component.css']
})
export class DemandeCreditComponent implements OnInit{
  demandeCreditForm!: FormGroup;
  creditId!: number;
  typeCredits: String[]=[
    'ANNUITE_CONSTANTE',
   'AMORTISSEMENT_CONSTANT',
    'EMPRUNT_ANFINE'
  ];
  selectedCredit!: Credit;
  demandecredit!: DemandeCredit


   constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private demandeCreditService: DemandeCreditService,
    private garantService: GarantorService,
    private creditservice: CreditService,
    private toastr: ToastrService,
    public dialog: MatDialog,
    private authService: AuthService
  ) {
    this.demandeCreditForm = this.formBuilder.group({
      amount: ['', Validators.required], // Exemple de contrôle de formulaire avec validation requise
      year:['', Validators.required], // Autres contrôles de formulaire
      typeCredit: ['', Validators.required],
      monthlyPaymentDate: ['', Validators.required],
      garant: this.formBuilder.group({
        nameGarantor: ['', Validators.required],
        secondnameGarantor: ['', Validators.required],
        salaryGarantor:['', Validators.required],
        workGarantor:['', Validators.required],
        urlimage: ['', Validators.required] // Assurez-vous que le nom du contrôle correspond exactement à celui dans le template HTML
      })
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('idcredit');
    if (idParam !== null) {
      this.creditId = +idParam;
      this.creditservice.findCredit(this.creditId).subscribe(
        (credit: Credit) => {
          // Stocker le crédit sélectionné
          this.selectedCredit = credit;
        }
      );

    } else {
      console.error("ID de crédit non fourni dans l'URL.");
    }

  }


  initForm(): void {
    this.demandeCreditForm = this.formBuilder.group({
      amount: ['', Validators.required,this.amountRangeValidator], // Exemple de contrôle de formulaire avec validation requise
      Year: ['', Validators.required], // Autres contrôles de formulaire
      typeCredit: ['', Validators.required],
      monthlyPaymentDate: ['', Validators.required],
      garant: this.formBuilder.group({
        nameGarantor:['', Validators.required],
        secondnameGarantor: ['', Validators.required],
        salaryGarantor: ['', Validators.required],
        workGarantor: ['', Validators.required],
        image: ['', Validators.required]// Assurez-vous que le nom du contrôle correspond exactement à celui dans le template HTML
      })
    });
  }
  amountRangeValidator(control: AbstractControl): ValidationErrors | null {
    const amount = control.value;
    const minAmount = this.selectedCredit.minamount; // Récupérez la valeur minamount du crédit sélectionné
    const maxAmount = this.selectedCredit.maxamount; // Récupérez la valeur maxamount du crédit sélectionné
    if (amount < minAmount || amount > maxAmount) {
      return { amountRange: true }; // Retourne une erreur si le montant est en dehors de la plage spécifiée
    }
    return null; // Retourne null si le montant est valide
  }



  submitDemandeCreditForm(): void {
    const formData = this.demandeCreditForm.value;
    const garantFormGroup = this.demandeCreditForm.get('garant');
    const token = localStorage.getItem('jwt');
    if(token){
        this.authService.getUserId(token).subscribe(
          id => {
            console.log('ID utilisateur récupéré:', id);
    if (garantFormGroup) {
        const newGarant: Garantor = garantFormGroup.value as Garantor;
        this.creditservice.findCredit(this.creditId).subscribe({
            next: (credit) => {
                // Vérifier si le montant se situe entre le minamount et le maxamount du crédit
                 if (formData.amount < credit.minamount || formData.amount > credit.maxamount) {
                    // Afficher une notification d'erreur
                    this.toastr.error('Le montant doit être compris entre ' + credit.minamount + ' et ' + credit.maxamount, 'Erreur');
                } else {
                    this.garantService.addGarant(newGarant).subscribe((garantResponse: any) => {
                        const garantId = garantResponse.idGarantor; // Supposons que "idGarantor" soit la propriété contenant l'ID du garant dans la réponse
                        console.log(garantId);
                        this.demandeCreditService.createNewCreditBasedOnExistingCredit(
                          formData as DemandeCredit,
                          this.creditId,
                          1,
                          id,
                          garantId // Utilisation de garantId ici
                      ).subscribe({
                          next: () => {
                              this.demandeCreditForm.reset();
                              console.log(formData.Reason);
                              this.openMessageDialog(formData.Reason);
                          },
                          error: error => {
                            const errorText = error.error.text;
                              console.error('Erreur lors de la création du crédit :', error);
                              this.openMessageDialog(errorText);
                              console.log(errorText);
                              // Gérer l'erreur ici, par exemple afficher une notification d'erreur

                          }
                      });

                    });
                 }
            },
        });
    } else {
        console.error('Le formulaire du garant est null.');
    }},
    error => {
      console.error('Erreur lors de la récupération de l\'ID utilisateur:', error);
      // Gérez l'erreur ici selon vos besoins
    }
  );
}else{
  console.error('Aucun token JWT trouvé dans le localStorage');

    }
}



  openMessageDialog(message: string): void {
    const dialogRef = this.dialog.open(PopUpCreditComponent, {
      width: '600px',
      data: message
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('Le dialogue de simulation a été fermé');
    });
  }
}
