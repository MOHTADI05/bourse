import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BodyfrontComponent } from './bodyfront.component';

describe('BodyfrontComponent', () => {
  let component: BodyfrontComponent;
  let fixture: ComponentFixture<BodyfrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BodyfrontComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BodyfrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
