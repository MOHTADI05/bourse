import { PopUpComponent } from './../pop-up/pop-up.component';
import { Component ,OnInit} from '@angular/core';
import { ProfilSolvabilite } from 'src/model/ProfilSolvabilite';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators,ValidatorFn,AbstractControl  } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';  
  import { ClusteringServiceService } from 'src/app/Services/clustering-service.service';
  import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
  import { MatDialog } from '@angular/material/dialog';




@Component({
  selector: 'app-profilsolva',
  templateUrl: './profilsolva.component.html',
  styleUrls: ['./profilsolva.component.css']
})
export class ProfilsolvaComponent implements OnInit {


  profilForm: FormGroup;

  
  constructor(public dialog: MatDialog ,private formBuilder: FormBuilder,private profilService: ClusteringServiceService) {
    this.profilForm = this.formBuilder.group({
      revenuMensuelNet: [null, Validators.required],
      chargeMensuel: [null, Validators.required],
      montantDetteTotale: [null, Validators.required],
      ancienneteEmploiActuel: [null, Validators.required],
      epargneActifsLiquides: [null, Validators.required],
      statutEmploi: [null, Validators.required],
      chargeFamille: [null, Validators.required],
      niveauEducation: [null, Validators.required],
      age: [null, Validators.required],
      regionResidence: [null, Validators.required],
      typeLogement: [null, Validators.required],
      situationMatrimoniale: [null, Validators.required]
    });
   }

  ngOnInit(): void {
   }

  profil: ProfilSolvabilite = {
    revenuMensuelNet: 0,
    chargeMensuel: 0,
    montantDetteTotale: 0,
    ratioDetteRevenu: 0,
    ancienneteEmploiActuel: 0,
    epargneActifsLiquides: 0,
    statutEmploi: '',
    chargeFamille: 0,
    niveauEducation: '',
    age: 0,
    regionResidence: '',
    typeLogement: '',
    situationMatrimoniale: ''
  };


  onSubmit(): void {
    if (this.profilForm.valid) {
      const profil: ProfilSolvabilite = this.profilForm.value;
      this.profilService.saveProfil(profil).subscribe(
        response => {
          console.log('Réponse du serveur:', response);
          this.openMessageDialog(response);

          // Traitez la réponse ici selon vos besoins
        },
        error => {
          console.error('Erreur lors de la sauvegarde du profil:', error);
          // Gérez l'erreur ici selon vos besoins
        }
      );    }
  }
  
  saveProfil(): void {
    this.profilService.saveProfil(this.profil).subscribe(
      response => {
        console.log('Réponse du serveur:', response);
        this.openMessageDialog(response);
      },
      error => {
        console.error('Erreur lors de la sauvegarde du profil:', error);
        // Gérez l'erreur ici selon vos besoins
      }
    );
  }

  openMessageDialog(message: string): void {
    const dialogRef = this.dialog.open(PopUpComponent, {
      width: '600px',
      data: message  
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('Le dialogue de simulation a été fermé');
    });
  }

}
