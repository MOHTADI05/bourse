import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PackFrontComponent } from './pack-front.component';

describe('PackFrontComponent', () => {
  let component: PackFrontComponent;
  let fixture: ComponentFixture<PackFrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PackFrontComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PackFrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
