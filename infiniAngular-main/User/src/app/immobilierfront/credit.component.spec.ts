import { ComponentFixture, TestBed } from '@angular/core/testing';

import {ImmobilierComponent} from "../immobilier/credit.component";

describe('CreditComponent', () => {
  let component: ImmobilierComponent;
  let fixture: ComponentFixture<ImmobilierComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImmobilierComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImmobilierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
