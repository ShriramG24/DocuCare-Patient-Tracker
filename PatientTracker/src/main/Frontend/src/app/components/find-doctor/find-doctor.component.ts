import { Component } from '@angular/core';

@Component({
  selector: 'app-find-doctor',
  templateUrl: './find-doctor.component.html',
  styleUrls: ['./find-doctor.component.css']
})
export class FindDoctorComponent {
  allDoctors = [
    { username: 'Dr. John Doe', field: 'Cardiology', finalRating: 4.5 },
    { username: 'Dr. John Doe', field: 'Cardiology', finalRating: 4.5 },
    { username: 'Dr. John Doe', field: 'Cardiology', finalRating: 4.5 },
    { username: 'Dr. John Doe', field: 'Cardiology', finalRating: 4.5 },
    // ... other doctors
  ]
}
