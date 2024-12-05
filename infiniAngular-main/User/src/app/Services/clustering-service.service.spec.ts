import { TestBed } from '@angular/core/testing';

import { ClusteringServiceService } from './clustering-service.service';

describe('ClusteringServiceService', () => {
  let service: ClusteringServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClusteringServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
