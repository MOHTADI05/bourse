import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InvHistoryComponent} from "./invhistory.component";

describe('InvhistoryComponent', () => {
  let component: InvHistoryComponent;
  let fixture: ComponentFixture<InvHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InvHistoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InvHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
