import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {InvHistory} from "../../model/InvHistory";

@Injectable({
  providedIn: 'root'
})
export class InvHistoryService {
  private apiUrl = 'http://localhost:8084/inv_history'; // Change this URL to your actual API endpoint

  constructor(private http: HttpClient) { }

  getAllInvHistory(): Observable<InvHistory[]> {
    return this.http.get<InvHistory[]>(this.apiUrl+'/all');
  }
  getDailyProfitLoss(userId: number): Observable<number> {
    return this.http.get<number>(this.apiUrl+'/daily-profit-loss/${userId}');
  }

  getDemandesCreditParClient(idClient: number): Observable<InvHistory[]> {
    return this.http.get<InvHistory[]>(`http://localhost:8084/inv_history/findbyclient/${idClient}`);
  }
}
