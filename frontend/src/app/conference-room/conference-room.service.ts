import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConferenceRoom } from './../conference-room';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root',
})
export class ConferenceRoomService {
  private createConferenceRoomLink = 'http://localhost:8080/conferenceRoom/add';
  private getAllConferenceRooms = 'http://localhost:8080/conferenceRoom/all';
  private changeConferenceRoomNameLink =
    'http://localhost:8080/corporation/update';
  private deleteConferenceRoomLink =
    'http://localhost:8080/conferenceRoom/delete';

  constructor(private http: HttpClient) {}

  getCorporation(): Observable<ConferenceRoom[]> {
    return this.http.get<ConferenceRoom[]>(
      this.getAllConferenceRooms,
      httpOptions
    );
  }
  createConferenceRoom(
    ConferenceRoom: ConferenceRoom,
    corporateId: number
  ): Observable<any> {
    const params = new HttpParams().set('corporate_id', corporateId);
    return this.http.post(this.createConferenceRoomLink, ConferenceRoom, {
      ...httpOptions,
      params,
    });
  }
  getConferenceRooms(
    corporateID: number | undefined
  ): Observable<ConferenceRoom[]> {
    return this.http.get<ConferenceRoom[]>(
      `http://localhost:8080/conferenceRoom/byCorpoId/${corporateID}`,
      httpOptions
    );
  }

  changeConferenceRoomName(conferenceRoom: ConferenceRoom): Observable<any> {
    return this.http.patch(
      `http://localhost:8080/conferenceRoom/update/${conferenceRoom.id}`,
      conferenceRoom,
      httpOptions
    );
  }

  deleteConferenceRoom(conferenceRoomId: number): Observable<any> {
    return this.http.delete(
      this.deleteConferenceRoomLink + '/' + conferenceRoomId,
      httpOptions
    );
  }
}
