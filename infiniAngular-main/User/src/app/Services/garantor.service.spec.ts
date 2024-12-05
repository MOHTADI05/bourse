import { TestBed } from '@angular/core/testing';

import { GarantorService } from './garantor.service';

describe('GarantorService', () => {
  let service: GarantorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GarantorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
