import { TestBed } from '@angular/core/testing';

import { BookingComponentService } from './booking-component.service';

describe('SendDateService', () => {
  let service: BookingComponentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BookingComponentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
