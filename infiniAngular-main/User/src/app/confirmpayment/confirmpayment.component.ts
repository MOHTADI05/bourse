import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { PaymentService } from '../Services/payment.service';

@Component({
  selector: 'app-confirmpayment',
  templateUrl: './confirmpayment.component.html',
  styleUrls: ['./confirmpayment.component.css']
})
export class ConfirmpaymentComponent implements OnInit {
  paymentId: number ;

  constructor(private route: ActivatedRoute, private paymentService: PaymentService) {
    this.paymentId = 0;
   }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.paymentId = params['paymentId'];
      if (this.paymentId>0) {
        this.confirmPayment();
      } else {
        this.showErrorAlert('Payment not Completed');
      }
    });
  }

  confirmPayment() {
    this.paymentService.confirmPayment(this.paymentId).subscribe(
      () => {
        this.showSuccessAlert('Payment confirmed successfully');
      },
      error => {
        console.error('Error confirming payment:', error);
        this.showErrorAlert('Failed to confirm payment');
      }
    );
  }

  showSuccessAlert(message: string) {
    Swal.fire('Success', message, 'success');
  }

  showErrorAlert(message: string) {
    Swal.fire('Error', message, 'error');
  }
}

