import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandesimulateurComponent } from './demandesimulateur.component';

describe('DemandesimulateurComponent', () => {
  let component: DemandesimulateurComponent;
  let fixture: ComponentFixture<DemandesimulateurComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DemandesimulateurComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DemandesimulateurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
