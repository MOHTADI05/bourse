import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PackCComponent } from './pack-c.component';

describe('PackCComponent', () => {
  let component: PackCComponent;
  let fixture: ComponentFixture<PackCComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PackCComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PackCComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
