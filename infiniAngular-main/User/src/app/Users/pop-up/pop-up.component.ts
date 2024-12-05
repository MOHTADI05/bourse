import { Component } from '@angular/core';
import {  Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-pop-up',
  templateUrl: './pop-up.component.html',
  styleUrls: ['./pop-up.component.css']
})
export class PopUpComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: string,  private dialogRef: MatDialogRef<PopUpComponent>) {
    console.log('Data received in CustomDialogComponentComponent:', data);
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
