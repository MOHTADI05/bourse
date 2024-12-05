import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
@Component({
  selector: 'app-pop-up-credit',
  templateUrl: './pop-up-credit.component.html',
  styleUrls: ['./pop-up-credit.component.css']
})
export class PopUpCreditComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: string,  private dialogRef: MatDialogRef<PopUpCreditComponent>) {
    console.log('Data received in CustomDialogComponentComponent:', data);
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
