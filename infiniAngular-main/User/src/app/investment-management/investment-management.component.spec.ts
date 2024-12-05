import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InvestmentManagementComponent } from './investment-management.component';

describe('InvestmentManagementComponent', () => {
  let component: InvestmentManagementComponent;
  let fixture: ComponentFixture<InvestmentManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InvestmentManagementComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InvestmentManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
