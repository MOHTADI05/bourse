import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailPackComponent } from './detail-pack.component';

describe('DetailPackComponent', () => {
  let component: DetailPackComponent;
  let fixture: ComponentFixture<DetailPackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DetailPackComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailPackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
