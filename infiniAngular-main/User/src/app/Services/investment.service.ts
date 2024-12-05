import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import {Investisment} from "../../model/investisment";
import {InvHistory} from "../../model/InvHistory";
@Injectable({
  providedIn: 'root'
})
export class InvestmentService {
  private apiUrl = 'http://localhost:8084/investisment';
  constructor(private http: HttpClient) {}

  getAllInvestisment(): Observable<Investisment[]> {
    return this.http.get<Investisment[]>(this.apiUrl+'/all');
  }
  buyPercentage(imbId: number, cin: number, amount: number): Observable<InvHistory> {
    const investmentRequest = {
      immobilierId: Math.round(imbId),
      cin: Math.round(cin),
      amount: Math.round(amount)
    };


    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    console.log('Sending investment request:', investmentRequest);

    return this.http.post<InvHistory>(`${this.apiUrl+'/buy'}`, investmentRequest, { headers })
      .pipe(
        catchError(error => {
          console.error('Error buying percentage', error);
          return throwError(error);
        })
      );
  }


  sellInvestment(imbId: number, amount: number): Observable<InvHistory[]> {
    return this.http.post<InvHistory[]>(`${this.apiUrl+'/sell'} `, { imbId, amount })
      .pipe(
        catchError(error => {
          console.error('Error selling investment', error);
          return throwError(error);
        })
      );
  }

  sellPercentageToDemand(demandId: number, sellerUserId: number): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

    return this.http.post<any>(`${this.apiUrl}/sellToDemand/${demandId}/${sellerUserId}`, {});
  }




}
