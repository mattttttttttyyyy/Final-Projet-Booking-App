import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CorporationComponent } from './corporation.component';

describe('CorporationComponent', () => {
  let component: CorporationComponent;
  let fixture: ComponentFixture<CorporationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CorporationComponent]
    });
    fixture = TestBed.createComponent(CorporationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
