import { TestBed } from '@angular/core/testing';

import { MyBackendService } from './my-backend.service';

describe('MyBackendService', () => {
  let service: MyBackendService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MyBackendService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
