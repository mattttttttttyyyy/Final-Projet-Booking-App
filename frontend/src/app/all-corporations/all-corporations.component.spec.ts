import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllCorporationsComponent } from './all-corporations.component';

describe('AllCorporationsComponent', () => {
  let component: AllCorporationsComponent;
  let fixture: ComponentFixture<AllCorporationsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AllCorporationsComponent]
    });
    fixture = TestBed.createComponent(AllCorporationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
