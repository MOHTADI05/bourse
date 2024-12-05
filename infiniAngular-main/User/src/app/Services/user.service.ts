import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { User } from './../../model/User';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private baseUrl: string ='http://localhost:8084/api/admin/users';
  private baseUrl1: string ='http://localhost:8084/api/user';

  private registerUrl: string ='http://localhost:8084/user/auth/register';
  private blockUrl: string = 'http://localhost:8084/api/admin/block/';
  private unblockUrl: string = 'http://localhost:8084/api/admin/unblock/';

  constructor(private http: HttpClient, private router: Router){}

  findAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.baseUrl);
  }

  private apiUrl = 'http://localhost:8084/user/auth/authenticate';
  

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  private handleError(error: any) {
    console.error(error);
    return throwError('Erreur! Veuillez réessayer.');
  }


  register(user: User): Observable<any> {
    return this.http.post<any>(`http://localhost:8084/user/auth/register`, user);
  }

  blockUser(cin: number): Observable<void> {
    return this.http.put<void>(`${this.blockUrl}${cin}`, null, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

  unblockUser(cin: number): Observable<void> {
    return this.http.put<void>(`${this.unblockUrl}${cin}`, null, this.httpOptions)
      .pipe(
        catchError(this.handleError)
      );
  }

///////////////////////////////////////RESET PASSWORD FUNCTIONS////////////////////////////////////////////////////////////////////

  requestPasswordReset(email: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl1}/restpassword/request`, { email });
  }

  validateResetToken(userEmail: string, code: string): Observable<string> {
    const url = `http://localhost:8084/api/user/validate-token/${userEmail}`;
    const body = { code: code };
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(url, body, { headers: headers, responseType: 'text' })
      .pipe(
        catchError(this.handleError)
      );
  }
  
   
  resetPassword(email: string, password: string): Observable<any> {
    return this.http.put<any>(`${this.baseUrl1}/new-password/${email}`, { password });
  }

///////////////////////////////////////END RESET PASSWORD FUNCTIONS////////////////////////////////////////////////////////////////////


///////////////////////////////////////     AGENT  FUNCTIONS    /////////////////////////////////////////////////////////////////////////

  addAgent(agent: User): Observable<User> {
    const token = localStorage.getItem('jwt'); // Récupérer le token JWT depuis le stockage local
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token // Ajouter le token JWT dans l'en-tête Authorization
    });
    return this.http.post<User>(`http://localhost:8084/api/admin/AddAgent`, agent, { headers: headers });
  }

  getAllAgents(role: string): Observable<User[]> {
    const token = localStorage.getItem('jwt');
     if (token) {    
      const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + token // Ajouter le token JWT dans l'en-tête Authorization
    });
    return this.http.get<User[]>(`http://localhost:8084/api/admin/GetallAgent?role=${role}`, { headers: headers });
  }else {
    console.error('Aucun token JWT trouvé dans le localStorage');
    this.router.navigate(['/login']);
    return this.http.get<User[]>(`http://localhost:8084/api/admin/GetallAgent?role=${role}`);

  }}

  saveClassification9box(id: number, per: string, pot: string): Observable<any> {
    const token = localStorage.getItem('jwt');
    if (token) {    
     const headers = new HttpHeaders({
     'Authorization': 'Bearer ' + token // Ajouter le token JWT dans l'en-tête Authorization
   });
    return this.http.get<any>(`http://localhost:8084/api/admin/9box/${id}/${per}/${pot}`, { headers: headers });
  }else {
    console.error('Aucun token JWT trouvé dans le localStorage');
    this.router.navigate(['/login']);
    return this.http.get<User[]>(`http://localhost:8084/api/admin/GetallAgent?role=AGENT`);

  }}



  

}
