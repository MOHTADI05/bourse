import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../../model/User";

@Injectable({
  providedIn: 'root'
})
export class ServiceuserService {
  private apiUrl = 'http://localhost:8084/user';

  constructor(private http: HttpClient) { }
  findAlluser(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl+'/all');
  }


}
