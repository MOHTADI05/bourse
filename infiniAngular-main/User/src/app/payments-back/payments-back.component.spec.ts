import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentsBackComponent } from './payments-back.component';

describe('PaymentsBackComponent', () => {
  let component: PaymentsBackComponent;
  let fixture: ComponentFixture<PaymentsBackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaymentsBackComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaymentsBackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
