import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'
import { Observable } from 'rxjs';
import { PackC } from 'src/model/PackC';
@Injectable({
  providedIn: 'root'
})
export class PackCService {

  readonly API_URL ="http://localhost:8084/Pack"

  constructor(private httpClient: HttpClient) { }
  findAllpack(): Observable<PackC[]>{
    return this.httpClient.get<PackC[]>(this.API_URL+'/read');
  }
  findpack(id:number): Observable<PackC>{
    return this.httpClient.get<PackC>(`${this.API_URL}/read/${id}`);
  }
  addCredit(packC: PackC) {
    return this.httpClient.post(this.API_URL+'/create', packC);
  }
  deleteCredit(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.API_URL}/delete/${id}`);
  }
  
}
