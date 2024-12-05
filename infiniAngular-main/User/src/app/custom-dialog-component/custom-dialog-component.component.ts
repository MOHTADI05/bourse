import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Amortissement } from 'src/model/Amortissement';

@Component({
  selector: 'app-custom-dialog-component',
  templateUrl: './custom-dialog-component.component.html',
  styleUrls: ['./custom-dialog-component.component.css']
})
export class CustomDialogComponentComponent {
  
 
  constructor(@Inject(MAT_DIALOG_DATA) public data: Amortissement,  private dialogRef: MatDialogRef<CustomDialogComponentComponent>) {
    console.log('Data received in CustomDialogComponentComponent:', data);
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
