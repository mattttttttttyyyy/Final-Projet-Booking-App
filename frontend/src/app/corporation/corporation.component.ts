import { Component, OnInit, Output } from '@angular/core';
import { CorporationService } from './corporation.service';
import { Corporation } from './../corporation';
import { HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError, Observable } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { AlertComponent } from '../alert/alert.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-corporation',
  templateUrl: './corporation.component.html',
  styleUrls: ['./corporation.component.css'],
})
export class CorporationComponent implements OnInit {
  corporations: Corporation[] = [];
  corporation = new Corporation('');
  errorMessage: any;
  corpoId: number | undefined;

  constructor(
    private corporationService: CorporationService,
    public dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCorporation();
  }

  createCorporationComponent(): void {
    this.corporationService
      .createCorporation(this.corporation)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 0) {
            console.error(
              'A client-side or network error occurred:',
              error.error
            );
          } else {
            this.errorMessage = `${error.error}`;
            this.openAlertDialog();
          }
          return throwError('Something bad happened; please try again later.');
        })
      )
      .subscribe(() => {
        console.log('success');
        this.corporationService
          .getCorporationIdByName(this.corporation.name.toLowerCase())
          .subscribe((corpoId: number) => {
            this.corpoId = corpoId;
            console.log(this.corpoId);
            this.router.navigate(['/corporation-main-page', this.corpoId]);
          });
      });
  }

  openAlertDialog() {
    this.dialog.open(AlertComponent, {
      data: {
        icon: 'Check',
        message: this.errorMessage,
      },
    });
  }

  loadCorporation(): void {
    this.corporationService
      .getCorporation()
      .subscribe((list: Corporation[]) => {
        this.corporations = list;
      });
  }
}
