import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmpaymentComponent } from './confirmpayment.component';

describe('ConfirmpaymentComponent', () => {
  let component: ConfirmpaymentComponent;
  let fixture: ComponentFixture<ConfirmpaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmpaymentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmpaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
