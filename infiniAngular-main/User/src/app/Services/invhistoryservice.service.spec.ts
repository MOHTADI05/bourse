import { TestBed } from '@angular/core/testing';

import { InvHistoryService } from './invhistoryservice.service';

describe('InvhistoryserviceService', () => {
  let service: InvHistoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InvHistoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
