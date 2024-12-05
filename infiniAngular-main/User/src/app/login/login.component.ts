import { User } from 'src/model/User';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './../Services/auth-service.service'; // Assurez-vous que le chemin est correct

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent { email: string = '';
password: string = '';
error: string = '';
token: string ='';
user: User = new User(); 
userbloked = false;
loginErreur = false;  
 

constructor(private authService: AuthService, private router: Router) { }
login(): void {
  this.authService.login({ email: this.email, password: this.password })
    .subscribe(
      (response: any) => {
        console.log(response.token);
        this.token = response.token;

        // Stocker le token JWT après la connexion réussie
        localStorage.setItem('jwt', this.token);
        // Récupérer le rôle de l'utilisateur
        this.authService.getUserRole(this.token).subscribe(
          (role: string) => {
            console.log('Role de l\'utilisateur :', role);
            if (role === 'ADMIN' ) {
              this.router.navigate(['/back/listUser']);
            } else if ( role === 'CLIENT') {
              this.router.navigate(['/front']);
            }else if ( role === 'AGENT') {
              this.router.navigate(['/back']);
            }
          },
          error => {
            console.error('Erreur lors de la récupération du rôle de l\'utilisateur :', error);
          }
        );this.authService.getUserBymail(this.email).subscribe(
          (user: User) => {
            this.user = user; // Utilisez les données de l'utilisateur comme vous le souhaitez
            // Faites quelque chose d'autre avec les données de l'utilisateur si nécessaire
            console.log('bloker ??:', this.userbloked);

            if (user.isbloked ) {
              console.log('fi west el if', this.userbloked);


            }else {
              this.userbloked=true;
            }
          },
          error => {
            console.error('Une erreur s\'est produite lors de la récupération de l\'utilisateur par le jeton :', error);
          }
        );
      
      },
      error => {
        console.error(error);
        this.loginErreur=true
        this.error = 'Email ou mot de passe incorrect.';
      }
    );
}
}
