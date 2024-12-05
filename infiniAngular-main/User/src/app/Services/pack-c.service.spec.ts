import { TestBed } from '@angular/core/testing';

import { PackCService } from './pack-c.service';

describe('PackCService', () => {
  let service: PackCService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PackCService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
