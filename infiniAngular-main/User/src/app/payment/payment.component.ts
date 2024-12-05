import { Component } from '@angular/core';
import { PaymentService } from '../Services/payment.service';
import { Payment } from 'src/model/Payment';
@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent {
  payments: Payment[] = []; // Initialize payments as an empty array
  paymentsSimu: Payment[] = [];
  mnttotl: number = 0; // Add this line
  period: number = 0; // Add this line
  interst: number = 0;
  constructor(private paymentService: PaymentService) { }

  ngOnInit(): void {
    this.getPayments();
  }
exportToExcel(mnttotl: number, period: number, interst: number): void {
  this.paymentService.exportToExcel(mnttotl, period, interst).subscribe(response => {
    // Handle the response here. The response should be a Blob containing the Excel file.
    // You might want to create a new Blob object, create an object URL for it, and then open that URL in a new window.
    let blob = new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    let url = window.URL.createObjectURL(blob);
    window.open(url);
  });
}
  getPayments(): void {
    this.paymentService.getAllPayments().subscribe(payments => this.payments = payments);
  }
  getPaymentsSimu(): void {
    this.paymentService.getPayments(this.mnttotl, this.period, this.interst).subscribe(payments => this.paymentsSimu = payments);
  }
}
