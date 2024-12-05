import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DemandeCredit } from 'src/model/DemandeCredit';
import { Observable, catchError, throwError } from 'rxjs';
import { Amortissement } from 'src/model/Amortissement';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class DemandeCreditService {

  readonly API_URL ="http://localhost:8084/DemandeCredit"

  constructor(private httpClient: HttpClient,  private toastr: ToastrService) { }
  createNewCreditBasedOnExistingCredit(
    request: DemandeCredit,
    id: number,
    id_fund: number,
    idC: number,
    id_garant: number
  ) {
    const url = `${this.API_URL}/create/${id}/${id_fund}/${idC}/${id_garant}`;
    return this.httpClient.post(url, request);
  }
  Simulateur(periode:number, montant:number):Observable<Amortissement>{
    return this.httpClient.get<Amortissement>(`${this.API_URL}/simulate/${montant}/${periode}`);
  }
  FichierExcel(periode: number, montant: number): Observable<Blob> {
    return this.httpClient.get(`${this.API_URL}/export/excel/${montant}/${periode}`, { responseType: 'blob' });
  }

  chart(idC: number): Observable<Map<number, number>> {
    return this.httpClient.get<Map<number, number>>(`${this.API_URL}/montantRestantParAnnee/${idC}`);
  }
  findCreditByUser(id: number): Observable<DemandeCredit[]>{
    return this.httpClient.get<DemandeCredit[]>(`${this.API_URL}/findbyclient/${id}`);
  }
  Pay(idC: number, fundId: number, CreditId: number ): Observable<any>{
    return this.httpClient.get<any>(`${this.API_URL}/findbyclient/${idC}/${fundId}/${CreditId}`);
  }
}
