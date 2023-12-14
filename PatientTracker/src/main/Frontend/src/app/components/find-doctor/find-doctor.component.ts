import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppointmentsService } from 'app/controller/appointments.service';
import { Doctor } from '../../models/doctor.model';

@Component({
  selector: 'app-find-doctor',
  templateUrl: './find-doctor.component.html',
  styleUrls: ['./find-doctor.component.css']
})
export class FindDoctorComponent {
  speciality: any;
  constructor(private route: ActivatedRoute, private appointmentService: AppointmentsService) { 

  }
  allDoctors:any;
  doctors : Doctor[]= [];
  ngOnInit(): void {
    // Example: Fetch data from the backend
    
    this.route.params.subscribe(params => {
      this.speciality = params['speciality'];
      console.log(this.speciality)
      // Use this.variableName as needed
    });
    this.appointmentService.getDoctorsofSpecialization(this.speciality).subscribe((data) => {
      console.log(data);
      this.doctors = data;
      this.allDoctors = data;
    }, (error) => {
      console.error('Error fetching doctor data', error);
    });
}
// allDoctors = [
//   { username: 'Dr. John Doe', field: 'Cardiology', finalRating: 4.5 },
//   { username: 'Dr. John Doe', field: 'Cardiology', finalRating: 4.5 },
//   { username: 'Dr. John Doe', field: 'Cardiology', finalRating: 4.5 },
//   { username: 'Dr. John Doe', field: 'Cardiology', finalRating: 4.5 },
//   // ... other doctors
// ]
}
