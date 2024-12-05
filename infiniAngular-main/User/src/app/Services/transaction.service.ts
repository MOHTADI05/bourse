import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Transaction } from 'src/model/Transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private apiUrl='http://localhost:8084/api/transactions';
  constructor(private http: HttpClient) {}


  getAllTransactions(): Observable<Transaction[]> {
  return this.http.get<Transaction[]>(this.apiUrl+'/getAllTransaction');
   }
  
   getTransactionsBySourceRIB(sourceRIB: number): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/sourceRIB/${sourceRIB}`);
  }
   
   update(transactionId: number, updatedTransaction: Transaction): Observable<Transaction> {
    return this.http.put<Transaction>(`${this.apiUrl}/${transactionId}`, updatedTransaction);
  }
  

  saveOrUpdateTransaction(Transaction: Transaction): Observable<Transaction> {
    return this.http.post<Transaction>(this.apiUrl+'/transfer', Transaction);
  }
  deleteTransactionById(transactionId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${transactionId}`);
}

getTransactionById(transactionId: number): Observable<Transaction> {
    return this.http.get<Transaction>(`${this.apiUrl}/get/${transactionId}`);
}

transferMoney(sourceAccountId: number, destinationAccountId: number, amount: number): Observable<string> {
  const queryParams = `?sourceAccountId=${sourceAccountId}&destinationAccountId=${destinationAccountId}&amount=${amount}`;
  const url = `${this.apiUrl}/transfer${queryParams}`;

  return this.http.post<string>(url, {});
}

depositMoney(destinationAccountId: number, amount: number, mail: string): Observable<string> {
  const queryParams = `?destinationAccountId=${destinationAccountId}&amount=${amount}&mail=${mail}`;
  const url = `${this.apiUrl}/deposit${queryParams}`;

  return this.http.post<string>(url, {});
}
withdrawMoney(sourceAccountId: number, amount: number, mail: string): Observable<string> {
  const queryParams = `?sourceAccountId=${sourceAccountId}&amount=${amount}&mail=${mail}`;
  const url = `${this.apiUrl}/Withdraws${queryParams}`;

  return this.http.post<string>(url, {});
}


}
  
