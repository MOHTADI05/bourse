import { TestBed } from '@angular/core/testing';

import { DemandeService } from './demandeservice.service';

describe('DemandeserviceService', () => {
  let service: DemandeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DemandeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
