import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Booking } from '../booking-component/booking';

@Component({
  selector: 'app-booking-calendar',
  templateUrl: './booking-calendar.component.html',
  styleUrls: ['./booking-calendar.component.css'],
})
export class BookingCalendarComponent implements OnChanges {
  @Input() bookings: Booking[] = [];

  ngOnChanges(changes: SimpleChanges) {
    console.log(this.bookings);
    if (changes['bookings'] && !changes['bookings'].firstChange) {
      // Bookings have changed, perform refresh logic here
      this.refreshCalendar();
    }
  }

  refreshCalendar() {
    console.log(this.bookings);
    // Update the bookings array with a new reference to trigger change detection
    this.bookings = this.bookings ? [...this.bookings] : [];
  }

  getFormattedTime(dateTime: string): string {
    const date = new Date(dateTime);
    const localTime =
      date.getHours().toString().padStart(2, '0') +
      ':' +
      date.getMinutes().toString().padStart(2, '0');
    return localTime;
  }

  getFormattedDate(dateTime: string): string {
    const date = new Date(dateTime);
    const localDate =
      date.getFullYear().toString().padStart(2, '0') +
      '-' +
      (date.getMonth() + 1).toString().padStart(2, '0') +
      '-' +
      date.getDate().toString().padStart(2, '0');

    return localDate;
  }

  // Other component code
}
