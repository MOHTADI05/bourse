import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Partner } from 'src/model/Partner';
@Injectable({
  providedIn: 'root'
})
export class PartnerService {


  private apiUrl = 'http://localhost:8084/partners'; // Mettez l'URL de votre API Spring Boot

  constructor(private http: HttpClient) { }

  getAllPartners(): Observable<Partner[]> {

    return this.http.get<Partner[]>(this.apiUrl+'/getAll');
  }

  getPartnerById(partnerId: number): Observable<Partner> {
    return this.http.get<Partner>(`${this.apiUrl+'/get/'}/${partnerId}`);
  }

  saveOrUpdatePartner(partner: Partner): Observable<Partner> {
    return this.http.post<Partner>(this.apiUrl+'/create', partner);
  }

 deletePartnerById(partnerId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl+'/delete/'}${partnerId}`);
}
}
