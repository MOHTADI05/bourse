import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProfilSolvabilite } from 'src/model/ProfilSolvabilite';


@Injectable({
  providedIn: 'root'
})
export class ClusteringServiceService {

  constructor(private http: HttpClient) { }

  performClustering(): Observable<any> {
    return this.http.get<any>('http://localhost:8084/user/auth/segmentation');
  }

  getCluster2Data(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8084/user/auth/cluster2');
  }

  getCluster1Data(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8084/user/auth/cluster1');
  }



  saveProfil(profil: ProfilSolvabilite): Observable<string> {
    return this.http.post<string>(`http://localhost:8084/api/solva/Profil`, profil, { responseType: 'text' as 'json' });
  }
}