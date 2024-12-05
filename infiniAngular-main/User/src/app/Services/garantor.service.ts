import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Garantor } from 'src/model/Garantor';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class GarantorService {
  readonly API_URL ="http://localhost:8084/Garantor"

  constructor(private httpClient: HttpClient) { }
  addGarant(garant: Garantor): Observable<number> {
    return this.httpClient.post<number>(this.API_URL+'/create', garant);
}

  addGarantAndAssigntToCredit(garant: Garantor, id: number) {
    return this.httpClient.post(`${this.API_URL}/create/${id}`, garant);
  }
}
