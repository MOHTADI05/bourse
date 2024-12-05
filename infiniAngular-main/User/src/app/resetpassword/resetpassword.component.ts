import { Component, OnInit } from '@angular/core';
import { UserService } from '../Services/User.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent {
 
  email: string='';
  code: string='';
  newPassword: string='';
  requestSuccessMessage: string='';
  requestErrorMessage: string='';
  validationSuccessMessage: string='';
  validationErrorMessage: string='';
  resetSuccessMessage: string='';
  resetErrorMessage: string='';
  
  resetRequested: boolean ; // Variable de contrôle pour afficher le champ de validation du token
  tokenValidated: boolean = false; // Variable de contrôle pour afficher le champ de réinitialisation du mot de passe

  constructor(private passwordResetService: UserService,private router: Router) {
    this.resetRequested = false; // Initialisation de la variable de contrôle
  }
  requestPasswordReset(): void {
    this.passwordResetService.requestPasswordReset(this.email).subscribe(

      (response) => {
        this.requestSuccessMessage = response;
      },
      (error) => {
        this.requestErrorMessage = error.error.message;

      }
    );

  }
  

  validateResetToken(): void {
    this.passwordResetService.validateResetToken(this.email, this.code)
      .subscribe(
        (response) => {
          this.validationSuccessMessage = response;
          this.tokenValidated = true; // Mettre à jour la variable de contrôle après une validation réussie du token
        },
        (error) => {
          this.validationErrorMessage = error;
        }
      );
  }

  resetPassword(): void {
    this.passwordResetService.resetPassword(this.email, this.newPassword).subscribe(
      response => {
        this.router.navigate(['/login']);
        this.resetSuccessMessage = response.message
        console.log(this.resetSuccessMessage );
        this.router.navigate(['/login']);


      },
      error => {
        this.resetErrorMessage = error.error.message;
      }
    );
  }
  


}
