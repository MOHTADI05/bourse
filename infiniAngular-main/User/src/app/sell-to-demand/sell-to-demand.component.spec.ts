import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellToDemandComponent } from './sell-to-demand.component';

describe('SellToDemandComponent', () => {
  let component: SellToDemandComponent;
  let fixture: ComponentFixture<SellToDemandComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SellToDemandComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellToDemandComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
