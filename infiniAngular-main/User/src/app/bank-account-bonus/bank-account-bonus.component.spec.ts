import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BankAccountBonusComponent } from './bank-account-bonus.component';

describe('BankAccountBonusComponent', () => {
  let component: BankAccountBonusComponent;
  let fixture: ComponentFixture<BankAccountBonusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BankAccountBonusComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BankAccountBonusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
