import { Component, Inject, ViewChild } from '@angular/core';
import { Amortissement } from 'src/model/Amortissement';
import { DemandeCreditService } from '../Services/demande-credit.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { catchError, of, tap } from 'rxjs';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CustomDialogComponentComponent } from '../custom-dialog-component/custom-dialog-component.component';
import { Route } from '@angular/router';

@Component({
  selector: 'app-simulate',
  templateUrl: './simulate.component.html',
  styleUrls: ['./simulate.component.css']
})
export class SimulateComponent {
  simulateurForm: FormGroup;
  amortissements: Amortissement[]=[];
  montantValue: number = 1000; // Valeur initiale du montant
  periodeValue: number = 12;
  constructor(private formBuilder: FormBuilder, private demandeCreditService: DemandeCreditService, private dialog: MatDialog) {
    this.simulateurForm = this.formBuilder.group({
      montantControl: [5000], // Valeur initiale du montant
      periodeControl: [12] // Valeur initiale de la période
    });
  }

  onMontantChange(event: any) {
    this.montantValue = event.target.value;
  }

  onPeriodeChange(event: any) {
    this.periodeValue = event.target.value;
  }

  onSubmit() {
    const montant = this.montantValue;
    const periode = this.periodeValue;
    console.log(montant)
    console.log(periode)
    this.demandeCreditService.Simulateur(periode, montant).pipe(
      tap((result: Amortissement) => {
        console.log(result)
        this.openDialog(result); // Ouvrir le dialog après la soumission du formulaire
      }),
      catchError((error) => {
        console.error('Erreur lors de la simulation :', error);
        // Gérer l'erreur ici, par exemple afficher une notification d'erreur
        return of(null); // Retourne un observable vide pour que le flux continue
      })
    ).subscribe();
  }
  
 
  openDialog(amortissement: Amortissement): void {
    // Ouvrir le dialogue avec les données d'amortissement
    const dialogRef = this.dialog.open(CustomDialogComponentComponent, {
      width: '600px',
      data: amortissement  // Passer l'amortissement au dialogue
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('Le dialogue de simulation a été fermé');
    });
  }
  
  closeDialog() {
    document.getElementById('dialog')!.style.display = 'none';
  }
  FichierExcel(){
    const montant = this.montantValue;
    const periode = this.periodeValue;
    this.demandeCreditService.FichierExcel(this.periodeValue, this.montantValue)
    .subscribe(
      (data: Blob) => {
        // Créer un objet URL pour le blob et créer un lien pour télécharger le fichier
        const url = window.URL.createObjectURL(data);
        const link = document.createElement('a');
        link.href = url;
        link.download = 'tableau_amortissement.xlsx';
        document.body.appendChild(link);
        link.click();
        // Libérer l'URL créé pour le blob
        window.URL.revokeObjectURL(url);
      },
      error => {
        console.error('Erreur lors du téléchargement du fichier Excel : ', error);
        // Gérer l'erreur de téléchargement
      }
    );
}



}

  

