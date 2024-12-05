import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomDialogComponentComponent } from './custom-dialog-component.component';

describe('CustomDialogComponentComponent', () => {
  let component: CustomDialogComponentComponent;
  let fixture: ComponentFixture<CustomDialogComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomDialogComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomDialogComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
