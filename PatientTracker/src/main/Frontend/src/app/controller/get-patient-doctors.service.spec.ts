import { TestBed } from '@angular/core/testing';

import { GetPatientDoctorsService } from './get-patient-doctors.service';

describe('GetPatientDoctorsService', () => {
  let service: GetPatientDoctorsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetPatientDoctorsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
