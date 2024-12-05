import { TestBed } from '@angular/core/testing';

import { ImmobilierService } from './immobilier.service';

describe('CreditService', () => {
  let service: ImmobilierService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImmobilierService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
