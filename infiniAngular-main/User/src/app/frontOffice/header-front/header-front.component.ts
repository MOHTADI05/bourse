import { Component } from '@angular/core';
import { AuthService } from 'src/app/Services/auth-service.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-header-front',
  templateUrl: './header-front.component.html',
  styleUrls: ['./header-front.component.css']
})
export class HeaderFrontComponent {
  jwtToken: string;  


  constructor(private authService: AuthService,private router: Router) {   this.jwtToken = localStorage.getItem('jwt') ?? '';}

  isLoggedIn(): boolean {
    // Vérifier si le jeton JWT est présent dans le stockage local
    return localStorage.getItem('jwt') !== null;
  }

  logout(): void {
    // Appel de la méthode de déconnexion du service d'authentification
    this.authService.logout(this.jwtToken).subscribe(
      response => {
        // Gérer la réponse si nécessaire
        console.log('Déconnexion réussie');
        // Effacer le token JWT du stockage local après la déconnexion
        localStorage.removeItem('jwt');
        this.router.navigate(['/login']);

      },
      error => {
        console.error('Erreur lors de la déconnexion :', error);
      }
    );
  }

}
