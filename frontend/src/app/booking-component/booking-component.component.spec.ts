import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingComponentComponent } from './booking-component.component';

describe('DatePickerComponent', () => {
  let component: BookingComponentComponent;
  let fixture: ComponentFixture<BookingComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookingComponentComponent]
    });
    fixture = TestBed.createComponent(BookingComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
