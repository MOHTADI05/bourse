import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {immobilier} from "../../model/Immobilier";
import {catchError} from "rxjs/operators";
@Injectable({
  providedIn: 'root'
})
export class ImmobilierService {
  private apiUrl = 'http://localhost:8084/immobilier';

  constructor(private http: HttpClient) {}

  getAllImmobiliers(): Observable<immobilier[]> {
    return this.http.get<immobilier[]>(this.apiUrl+'/all');
  }

  addImmobilier(immobilier: immobilier): Observable<immobilier> {

    return this.http.post<immobilier>(`${this.apiUrl+'/add'}`, immobilier);
  }

  upupdateImmobilier(immobilier: immobilier): Observable<immobilier> {
    return this.http.put<immobilier>(`${this.apiUrl+'/update'}`, immobilier);
  }


  deleteImmobilier(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }

  getImmobilierById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/find/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }
  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }

}
