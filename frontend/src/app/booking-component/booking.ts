export class Booking {
  'startTime': String;
  'endTime': String;
  'uniqueId': string;
  'conferenceRoom': number;
  'id': number;
  static startDate: string;
  constructor(
    startTime: String,
    endTime: String,
    uniqueId?: string,
    conferenceRoom?: number,
    id?: number
  ) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.uniqueId = uniqueId!;
    this.conferenceRoom = conferenceRoom!;
    this.id = id!;
  }
}
