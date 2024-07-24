import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css'],
})
export class AlertComponent {
  message: string = 'An unspecified error has occurred';
  icon: string = '';
  buttonText = 'Ok';

  constructor(
    @Inject(MAT_DIALOG_DATA)
    private data: {
      message: string;
      icon: string;
      buttonText: string;
    },
    private dialogRef: MatDialogRef<AlertComponent>
  ) {
    if (data?.icon) this.icon = data.icon;
    if (data?.message) this.message = data.message;
    if (data?.buttonText) this.buttonText = data.buttonText;
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
