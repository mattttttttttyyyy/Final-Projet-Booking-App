import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookingCalendarComponent } from './booking-calendar.component';

@NgModule({
  declarations: [BookingCalendarComponent],
  imports: [CommonModule],
  exports: [BookingCalendarComponent],
})
export class BookingCalendarModule {}
