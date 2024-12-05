import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopUpCreditComponent } from './pop-up-credit.component';

describe('PopUpCreditComponent', () => {
  let component: PopUpCreditComponent;
  let fixture: ComponentFixture<PopUpCreditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PopUpCreditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PopUpCreditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
