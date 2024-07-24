import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AlertComponent } from './alert/alert.component';

@Injectable({
  providedIn: 'root',
})
export class DialogService {
  constructor(private dialog: MatDialog) {}

  openDialog(text: string): void {
    const dialogRef = this.dialog.open(AlertComponent, {
      data: {
        message: text,
        buttonText: 'Close',
      },
    });

    dialogRef.afterClosed().subscribe(() => {
      // Perform any necessary actions after the dialog is closed
    });
  }
}
