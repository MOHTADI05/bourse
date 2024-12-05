import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private http:HttpClient) { }
  private baseUrl = 'https://newsapi.org/v2';
  private apiKey = '802b709136e046abbe4ffe9e5549ab8c';
  getNews(fromDate: string): Observable<any> {
    const url = `${this.baseUrl}/everything?q=bank&from=${fromDate}&sortBy=publishedAt&apiKey=${this.apiKey}`;
    return this.http.get(url).pipe(
      map((response: any) => response.articles)
    );
  }
}
