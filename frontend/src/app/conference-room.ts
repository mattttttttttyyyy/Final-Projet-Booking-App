export class ConferenceRoom {
  'id': number;
  'name': String;
  'corporation_id': number;
  'level': number;
  'sittingNumber': number;
  'standingNumber': number;

  constructor(
    name: String,
    level: number,
    sittingNumber: number,
    standingNumber: number
  ) {
    this.name = name;
    this.level = level;
    this.sittingNumber = sittingNumber;
    this.standingNumber = standingNumber;
  }
}
