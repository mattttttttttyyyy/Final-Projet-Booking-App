import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CorporationMainPageComponent } from './corporation-main-page.component';

describe('CorporationMainPageComponent', () => {
  let component: CorporationMainPageComponent;
  let fixture: ComponentFixture<CorporationMainPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CorporationMainPageComponent]
    });
    fixture = TestBed.createComponent(CorporationMainPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
