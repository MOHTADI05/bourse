import { Component } from '@angular/core';
import { Payment } from 'src/model/Payment';
import { PaymentService } from '../Services/payment.service';
import { OnInit } from '@angular/core';
@Component({
  selector: 'app-payments-back',
  templateUrl: './payments-back.component.html',
  styleUrls: ['./payments-back.component.css']
})
export class PaymentsBackComponent implements OnInit{
  payments: Payment[] = []; // Initialize payments as an empty array
  constructor(private paymentService: PaymentService) { }

  ngOnInit(): void {
    this.getPayments();
  }

  getPayments(): void {
    this.paymentService.getAllPayments().subscribe(payments => this.payments = payments);
  }

}
