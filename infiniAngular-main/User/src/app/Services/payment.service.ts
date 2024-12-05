import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Payment } from 'src/model/Payment';
import { HttpParams } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private baseUrl = 'http://localhost:8084/payments'; // L'URL de votre API Spring Boot

  constructor(private http: HttpClient) { }

  getPaymentByNumber(paymentNumber: number): Observable<Payment> {
    const url = `${this.baseUrl+'/getall'}${paymentNumber}`;
    return this.http.get<Payment>(url);
  }
  exportToExcel(mnttotl: number, period: number, interst: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/export/excel/${mnttotl}/${period}/${interst}`, { responseType: 'blob' });
  }
  createPayment(payment: Payment): Observable<Payment> {
    return this.http.post<Payment>(this.baseUrl+'/create', payment);
  }

  confirmPayment(paymentNumber: number): Observable<Payment> {
    const url = `${this.baseUrl}/${paymentNumber}/confirm`;
    return this.http.get<Payment>(url);
  }

  getAllPayments(): Observable<Payment[]> {
return this.http.get<Payment[]>(this.baseUrl);
  }

  getPayments(mnttotl: number, period: number, interst: number): Observable<Payment[]> {
    return this.http.get<Payment[]>(`${this.baseUrl}/payments/${mnttotl}/${period}/${interst}`);
  }

  updatePayment(paymentNumber: number, updatedPayment: Payment): Observable<Payment> {
    const url = `${this.baseUrl}/${paymentNumber}`;
    return this.http.put<Payment>(url, updatedPayment);
  }

  deletePayment(paymentNumber: number): Observable<void> {
    const url = `${this.baseUrl}/${paymentNumber}`;
    return this.http.delete<void>(url);
  }
  createPaymentIntent(amount: number): Observable<any> {
    const params = new HttpParams()
      .set('amount', (amount * 100).toString()) // Convertir en centimes
      .set('currency', 'eur')
      .set('payment_method_types', 'card');

    return this.http.post(`${this.baseUrl}/createPaymentIntent`, params);
  }
  getMonthlyInterestRate(interestRate: number): number {
    return interestRate / 100 / 12;
  }
  nfPercent: Intl.NumberFormat = new Intl.NumberFormat('en-US', {
    style: 'percent',
    minimumFractionDigits: 2,
    maximumFractionDigits: 4
  });

  nfCurrency: Intl.NumberFormat = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  });
  formatCurrency(number: number): string {
    return this.nfCurrency.format(number);
  }

  formatPercent(number: number): string {
    return this.nfPercent.format(number);
  }

  stringToPercent(s: string): number {
    return parseFloat(s.replace('%', '')) / 100;
  }
  pmt(r: number, nper: number, pv: number, fv: number, type: number): number {
    if (r === 0) {
      return -(pv + fv) / nper;
    }

    let pmt = r / (Math.pow(1 + r, nper) - 1) * -(pv * Math.pow(1 + r, nper) + fv);

    if (type === 1) {
      pmt /= (1 + r);
    }

    return pmt;
  }

  fv(r: number, nper: number, c: number, pv: number, type: number): number {
    if (r === 0) {
      return pv;
    }

    if (type === 1) {
      c *= (1 + r);
    }

    return -((Math.pow(1 + r, nper) - 1) / r * c + pv * Math.pow(1 + r, nper));
  }
  ipmt(r: number, per: number, nper: number, pv: number, fv: number, type: number): number {
    let ipmt = this.fv(r, per - 1, this.pmt(r, nper, pv, fv, type), pv, type) * r;

    if (type === 1) {
      ipmt /= (1 + r);
    }

    return ipmt;
  }

  ppmt(r: number, per: number, nper: number, pv: number, fv: number, type: number): number {
    return this.pmt(r, nper, pv, fv, type) - this.ipmt(r, per, nper, pv, fv, type);
  }
}
