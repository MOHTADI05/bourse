import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilsolvaComponent } from './profilsolva.component';

describe('ProfilsolvaComponent', () => {
  let component: ProfilsolvaComponent;
  let fixture: ComponentFixture<ProfilsolvaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfilsolvaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfilsolvaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
