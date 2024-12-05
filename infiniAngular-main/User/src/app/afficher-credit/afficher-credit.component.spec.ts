import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AfficherCreditComponent } from './afficher-credit.component';

describe('AfficherCreditComponent', () => {
  let component: AfficherCreditComponent;
  let fixture: ComponentFixture<AfficherCreditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AfficherCreditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AfficherCreditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
