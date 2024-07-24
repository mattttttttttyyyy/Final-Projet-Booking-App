import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CorporationModule } from './corporation/corporation.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BookingComponentModule } from './booking-component/booking-component.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { ConferenceRoomModule } from './conference-room/conference-room.module';
import { JsonPipe, NgIf } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbTimepickerModule } from '@ng-bootstrap/ng-bootstrap';
import { AlertComponent } from './alert/alert.component';
import { MatDialogModule } from '@angular/material/dialog';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { RouterModule } from '@angular/router';
import { BookingComponentComponent } from './booking-component/booking-component.component';
import { CorporationComponent } from './corporation/corporation.component';
import { CorporationMainPageComponent } from './corporation-main-page/corporation-main-page.component';
import { CorporationMainPageModule } from './corporation-main-page/corporation-main-page/corporation-main-page.module';
import { BookingCalendarComponent } from './booking-calendar/booking-calendar.component';
import { AllCorporationsComponent } from './all-corporations/all-corporations.component';
import { BookingUpdateComponent } from './booking-update/booking-update.component';
import { BookingUpdateModule } from './booking-update/booking-update.module';

@NgModule({
  declarations: [
    AppComponent,
    AlertComponent,
    NavBarComponent,
    AllCorporationsComponent,
  ],
  imports: [
    BrowserModule,
    NgbModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    BookingComponentModule,
    HttpClientModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule,
    CorporationModule,
    ConferenceRoomModule,
    ReactiveFormsModule,
    NgbTimepickerModule,
    JsonPipe,
    NgIf,
    MatDialogModule,
    BookingUpdateModule,
    CorporationMainPageModule,
    RouterModule.forRoot([
      { path: 'booking', component: BookingComponentComponent },
      { path: '', component: CorporationComponent },
      { path: 'allcorporations', component: AllCorporationsComponent },
      { path: 'booking-update', component: BookingUpdateComponent },
    ]),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
