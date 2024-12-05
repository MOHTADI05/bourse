import { User } from './../../model/User';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import * as jwt_decode from 'jwt-decode';
 

import { catchError, tap } from 'rxjs/operators';
 
const BASE_URL = 'http://localhost:8084/user/auth/authenticate';
const SECRET_KEY="9E9YcOHovjs3QvOeyGLW7411eUo8d0KPV085ASgf0jZlobwOIj8jPz4kMekmz3ttYvTif6hNkY4kbscloVfXHBDcPN28DWeQzaAA3asVTnkuDy+JymB+/Wt9ilNCTEr8q6555HEYHaSuOvh+m8WxyZx6XG+hwh01Ul1P1cgIp4CNfCqL4Oiwtcex1R4UIzI2WZ8QcCUMZiOEhsa+CDG+YDO0xBlFygnRu6ktsfCNVUVAsaRSC8afzm6lrPdYLBqPrFLaMp3X+XUkJA3E3opjko3a0MKUOOmGy2sdJOPT/rDTldSti87ykwZIcZG7ARlek64WtUuklipBV9lMWekFlX9gDHYW4Qisnm3z3z/FRQE=";



@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // URL de l'API Spring Boot pour l'authentification
  private apiUrl = 'http://localhost:8084/user/auth/authenticate';



  constructor(private http: HttpClient) { }

  register(signRequest: any): Observable<any> {
    return this.http.post(BASE_URL + 'signup', signRequest);
  }
  login(loginRequest: any): Observable<any> {
    return this.http.post(BASE_URL , loginRequest).pipe(
      tap((response: any) => {
        // Récupérer le jeton JWT de la réponse
        const jwtToken = response.token;
        // Stocker le jeton JWT dans le stockage local
        localStorage.setItem('jwt', jwtToken);
      }),
      catchError((error) => {
        // Gérer les erreurs de connexion
        return throwError(error);
      })
    );
  }

  isLoggedIn(): boolean {
    // Vérifier si le jeton JWT est présent dans le stockage local
    return localStorage.getItem('jwt') !== null;
  }


   
  
  getUserRole(jwtToken: string): Observable<string> {
    // Appel HTTP GET pour récupérer le rôle de l'utilisateur
    return this.http.get<string>(`http://localhost:8084/user/auth/userRole/${jwtToken}`);
  }
  

  private createAuthorizationHeader(): HttpHeaders | null {
    const jwtToken = localStorage.getItem('jwt');

    if (jwtToken) {
   ;

      console.log("JWT token found in local storage", jwtToken);
      return new HttpHeaders().set("Authorization", "Bearer " + jwtToken);
    } else {
      console.log("JWT token not found in local storage");
      return null; // Aucun jeton trouvé, renvoyer null
    }
  }


  getUserByToken(jwtToken: string): Observable<User> {
    return this.http.get<User>(`http://localhost:8084/user/auth/userbytoken/${jwtToken}`);
  }


  getUserBymail(jwtToken: string): Observable<User> {
    return this.http.get<User>(`http://localhost:8084/user/auth/userbymail/${jwtToken}`);
  }

  logout(jwtToken: string): Observable<any> {
    return this.http.get<any>(`http://localhost:8084/user/auth/logout/${jwtToken}`);
  }
  
  getUserId(jwtToken: string): Observable<number> {
    return this.http.get<number>(`http://localhost:8084/user/auth/userid/${jwtToken}`);
  }
 
}
