import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DateToSend } from '../date-to-send';
import { Booking } from './booking';

@Injectable({
  providedIn: 'root',
})
export class BookingComponentService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}
  deleteBooking(id: number) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    console.log('deleteBooking, booking ID to delete: ' + id);
    const url = `${this.baseUrl}/booking/deleteByID/${id}`;
    console.log('deleteBooking, url: ' + url);
    this.http.delete(url, httpOptions).subscribe(
      () => {
        console.log('Booking deleted successfully.');
      },
      (error) => {
        console.log('Error deleting booking:', error);
      }
    );
  }

  getBookingByUniqueId(uniqueId: string): Observable<Booking> {
    const url = `${this.baseUrl}/booking/byUniqueID/${uniqueId}`;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.get<Booking>(url, httpOptions);
  }
  getConferenceRoomID(unigueID: string): Observable<number> {
    const url = `${this.baseUrl}/booking/roomID/${unigueID}`;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.get<number>(url, httpOptions);
  }

  postDate(dateToSend: DateToSend): Observable<any> {
    const url = `${this.baseUrl}/booking/add`;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.post(url, dateToSend, httpOptions);
  }

  createBooking(booking: Booking, roomId: number): Observable<any> {
    const url = `${this.baseUrl}/booking/add/${roomId}`;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.post(url, booking, httpOptions);
  }

  getBookingsByDateAndRoom(date: Date, roomId: number): Observable<Booking[]> {
    const url = `${this.baseUrl}/booking/byDateAndRoom`;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      params: new HttpParams()
        .set('date', date.toISOString())
        .set('roomId', roomId.toString()),
    };
    return this.http.get<Booking[]>(url, httpOptions);
  }
  updateBooking(uniqueId: string, booking: Booking): void {
    this.http.patch(`${this.baseUrl}/booking/update/${uniqueId}`, booking);
  }

  editBooking(bookingUniqueId: string, Booking: Booking): Observable<Booking> {
    const url = `${this.baseUrl}/booking/update/${bookingUniqueId}`;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.patch<Booking>(url, Booking, httpOptions);
  }
}
