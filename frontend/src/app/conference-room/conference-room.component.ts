import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ConferenceRoomService } from './conference-room.service';
import { ConferenceRoom } from './../conference-room';
import { catchError, map, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { handleError } from '../error-handler';
import { MatDialog } from '@angular/material/dialog';
import { NgbCollapseModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-conference-room',
  templateUrl: './conference-room.component.html',
  styleUrls: ['./conference-room.component.css'],
})
export class ConferenceRoomComponent implements OnInit {
  room!: ConferenceRoom;
  isTextFieldOpen: boolean = false;
  newName!: String;
  newRoomName: String = '';
  roomNameToChange: number | undefined;
  public isCollapsed = true;

  openTextField(room: ConferenceRoom): void {
    this.roomNameToChange = room.id;
    this.room = room;
    this.isTextFieldOpen = true;
    this.newName = room.name;
  }
  @Input() corporationId: number | undefined;
  @Output() selectRoom: EventEmitter<number> = new EventEmitter<number>();

  conferenceRooms: ConferenceRoom[] = [];
  conferenceRoom = new ConferenceRoom('', 1, 1, 1);
  selectedRoomNumber: number | undefined;

  constructor(
    private conferenceRoomService: ConferenceRoomService,
    public dialog: MatDialog
  ) {
    this.conferenceRoom = new ConferenceRoom('', 1, 1, 1);
  }

  ngOnInit(): void {
    if (this.corporationId !== undefined) {
      this.loadConferenceRooms();
    } else {
      console.error(
        'Corporation ID is undefined. Unable to load conference rooms.'
      );
    }
  }
  errorMessage: string = '';

  changeName(newName: String): void {
    if (this.room) {
      this.room.name = newName;
      this.conferenceRoomService.changeConferenceRoomName(this.room).subscribe(
        () => {
          console.log('Conference room name changed successfully.');
          this.isTextFieldOpen = false;
          this.loadConferenceRooms();
        },
        (error) => {
          console.error('Error changing conference room name:', error);
          if (
            error.status === 400 &&
            error.error &&
            typeof error.error === 'string'
          ) {
            this.errorMessage = error.error;
          }
        }
      );
    }
  }

  deleteConferenceRoom(roomId: number): void {
    this.conferenceRoomService.deleteConferenceRoom(roomId).subscribe(
      () => {
        console.log('Conference room deleted successfully.');
        this.loadConferenceRooms();
      },
      (error) => {
        console.error('Error deleting conference room:', error);
      }
    );
  }

  loadConferenceRooms(): void {
    this.conferenceRoomService.getConferenceRooms(this.corporationId).subscribe(
      (list: ConferenceRoom[]) => {
        this.conferenceRooms = list;
      },
      (error) => {
        console.error('Error loading conference rooms:', error);
      }
    );
  }

  createConferenceRoom(): void {
    if (this.corporationId !== undefined) {
      this.conferenceRoomService
        .createConferenceRoom(this.conferenceRoom, this.corporationId)
        .pipe(
          catchError((error: HttpErrorResponse) => {
            handleError(error, this.dialog);
            return throwError(error);
          })
        )
        .subscribe(() => {
          this.loadConferenceRooms();
          this.conferenceRoom = new ConferenceRoom('', 1, 1, 1);
        });
    } else {
      console.error(
        'Corporation ID is undefined. Unable to create conference room.'
      );
    }
  }

  onRoomSelected(roomId: number): void {
    console.log('Room selected:', roomId);
    this.selectedRoomNumber = roomId;
    this.selectRoom.emit(roomId);
  }
}
