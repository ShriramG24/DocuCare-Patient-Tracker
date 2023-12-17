import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindDoctorComponent } from './find-doctor.component';

describe('FindDoctorComponent', () => {
  let component: FindDoctorComponent;
  let fixture: ComponentFixture<FindDoctorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FindDoctorComponent]
    });
    fixture = TestBed.createComponent(FindDoctorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
