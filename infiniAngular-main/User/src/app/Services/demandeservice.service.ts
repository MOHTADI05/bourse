// demande.service.ts
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Demand} from "../../model/demande";

@Injectable({
  providedIn: 'root'
})
export class DemandeService {

  private apiUrl = 'http://localhost:8084/demande'; // Change the port if different

  constructor(private http: HttpClient) { }

  getAllDemands(): Observable<Demand[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

    return this.http.get<Demand[]>(`${this.apiUrl}/all`);
  }

  getDemandById(id: number): Observable<Demand> {
    return this.http.get<Demand>(`${this.apiUrl}/find/${id}`);
  }
  // Assuming this is in your investment.service.ts or a relevant service file
  getDemandsForImmobilier(immobilierId: number): Observable<any[]> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');

    console.log(`Fetching from URL: http://localhost:8080/api/demands/${immobilierId}`);
    return this.http.get<any[]>(`http://localhost:8084/demande/${immobilierId}`);
  }


}
