import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'
import { Observable } from 'rxjs';
import { Credit } from 'src/model/Credit';
@Injectable({
  providedIn: 'root'
})
export class CreditService {
  readonly API_URL ="http://localhost:8084/Credit"

  constructor(private httpClient: HttpClient) { }
  findAllCredit(): Observable<Credit[]>{
    return this.httpClient.get<Credit[]>(this.API_URL+'/read');
  }
  findCredit(id:number): Observable<Credit>{
    return this.httpClient.get<Credit>(`${this.API_URL}/read/${id}`);
  }
  findCreditByPack(id: number): Observable<Credit[]>{
    return this.httpClient.get<Credit[]>(`${this.API_URL}/readCreditPack/${id}`);
  }
  addCredit(credit: Credit) {
    return this.httpClient.post(this.API_URL+'/create', credit);
  }
  deleteCredit(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.API_URL}/delete/${id}`);
  }
  addCreditandAssignToPack(credit: Credit, id: number): Observable<Credit> {
    return this.httpClient.post<Credit>(`${this.API_URL}/add/${id}`, credit);
  }
  updateCreditandAssignToPack(credit: Credit, id: number, idP: number): Observable<Credit> {
    return this.httpClient.put<Credit>(`${this.API_URL}/add/${id}/${idP}`, credit);
  }
  
}
