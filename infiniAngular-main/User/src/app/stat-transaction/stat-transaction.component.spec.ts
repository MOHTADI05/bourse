import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatTransactionComponent } from './stat-transaction.component';

describe('StatTransactionComponent', () => {
  let component: StatTransactionComponent;
  let fixture: ComponentFixture<StatTransactionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StatTransactionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StatTransactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
