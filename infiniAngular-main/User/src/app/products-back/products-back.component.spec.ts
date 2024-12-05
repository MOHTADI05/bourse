import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductsBackComponent } from './products-back.component';

describe('ProductsBackComponent', () => {
  let component: ProductsBackComponent;
  let fixture: ComponentFixture<ProductsBackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductsBackComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductsBackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
