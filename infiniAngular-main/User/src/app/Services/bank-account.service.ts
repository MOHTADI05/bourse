import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BankAccount } from 'src/model/BankAccount';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
  
})
export class BankAccountService {
  private apiUrl='http://localhost:8084/api/bankAccounts';
  constructor(private http: HttpClient) {}
  getAllbankAccounts(): Observable<BankAccount[]> {
    return this.http.get<BankAccount[]>(this.apiUrl);
     }
  
  getAll(): Observable<BankAccount[]> {
      return this.http.get<BankAccount[]>(this.apiUrl+'/show');
       }
  createBankAccount(bankAccount: BankAccount): Observable<number> {
    return this.http.post<number>(`${this.apiUrl}`, bankAccount);
  }

  addBonusToAccount(code: number): Observable<string> {
    const url = `${this.apiUrl}/add-bonus/${code}`;
    
    
    return this.http.post<string>(url, {responseType: 'text'}).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'Error occurred while adding bonus.';
        if (error.error instanceof ErrorEvent) {
          // Client-side error
          errorMessage = `An error occurred: ${error.error.message}`;
        } else {
          // Server-side error
          errorMessage = error.error || errorMessage;
        }
        return throwError(errorMessage);
      })
    );
  }
  updateBankAccount(rib: number, bankAccount: BankAccount): Observable<number> {
    const url = `${this.apiUrl}/modifier/${rib}`;
    return this.http.put<number>(url, bankAccount);
  }
  generateQRCodeForAccount(rib: number): Observable<Blob> {
    return this.http.post(`${this.apiUrl}/generate-code/${rib}`, {}, {
      responseType: 'blob'
    });
  }

  calculateTotalCashFlow(numberOfMonths: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/calculateTotalCashFlow/${numberOfMonths}`);
  }
  
     
   update(rib: number, updatedBankAccount: BankAccount): Observable<BankAccount> {
    return this.http.put<BankAccount>(`${this.apiUrl}/${rib}`, updatedBankAccount);
  }
  

  saveOrUpdateBankAccount(BankAccount: BankAccount): Observable<BankAccount> {
    return this.http.post<BankAccount>(this.apiUrl, BankAccount);
  }
  deleteBankAccountById(rib: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${rib}`);
}

getBankAccountByRib(rib: number): Observable<BankAccount> {
    return this.http.get<BankAccount>(`${this.apiUrl}/getbyrib/${rib}`);
}
calculateScore(bankAccountId: number): Observable<string> {
  const url = `${this.apiUrl}/calculateScore/${bankAccountId}`;
  return this.http.get(url, { responseType: 'text' });
}
generateAndSetCodeForAccount(accountId: number): Observable<Uint8Array> {
  const url = `${this.apiUrl}/generate-code/${accountId}`;
  
  return this.http.post(url, {}, {
    responseType: 'arraybuffer', // Change responseType to 'arraybuffer'
    headers: new HttpHeaders().set('Accept', 'image/png'),
  }).pipe(
    map((response: ArrayBuffer) => new Uint8Array(response)), // Convert ArrayBuffer to Uint8Array
    catchError(this.handleError)
  );
}

private handleError(error: any): Observable<never> {
  console.error('An error occurred:', error);
  return throwError('Something went wrong, please try again later.');
}
}
