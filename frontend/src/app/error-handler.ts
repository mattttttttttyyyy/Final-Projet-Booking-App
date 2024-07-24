import { HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';
import { AlertComponent } from './alert/alert.component';
import { MatDialog } from '@angular/material/dialog';

export function handleError(error: HttpErrorResponse, dialog: MatDialog) {
  if (error.status === 0) {
    console.error('A client-side or network error occurred:', error.error);
  } else {
    const errorMessage = `${error.error}`;
    openAlertDialog(dialog, errorMessage);
  }
  return throwError('Something bad happened; please try again later.');
}

export function openAlertDialog(dialog: MatDialog, errorMessage: string) {
  dialog.open(AlertComponent, {
    data: {
      icon: 'Check',
      message: errorMessage,
    },
  });
}
