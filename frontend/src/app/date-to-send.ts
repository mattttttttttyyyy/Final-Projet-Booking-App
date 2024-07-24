import { DateFormatterOptions } from 'ngx-bootstrap/chronos/types';

export class DateToSend {
  'startTime': String;
  'endTime': String;
  static startDate: string;
  constructor(date: String, time: String) {
    this.startTime = date;
    this.endTime = time;
  }
}
