import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllTemplatefrontComponent } from './all-templatefront.component';

describe('AllTemplatefrontComponent', () => {
  let component: AllTemplatefrontComponent;
  let fixture: ComponentFixture<AllTemplatefrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllTemplatefrontComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllTemplatefrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
