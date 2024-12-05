import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsFrontComponent } from './products-front.component';

describe('ProductsFrontComponent', () => {
  let component: ProductsFrontComponent;
  let fixture: ComponentFixture<ProductsFrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductsFrontComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductsFrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
