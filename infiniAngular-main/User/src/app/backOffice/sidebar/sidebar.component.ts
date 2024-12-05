import { Component } from '@angular/core';
import { AuthService } from 'src/app/Services/auth-service.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  jwtToken: string; // Déclaration de la variable jwtToken

  constructor(private authService: AuthService,private router: Router) {   this.jwtToken = localStorage.getItem('jwt') ?? '';  // Si null, assigne une chaîne vide
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
