import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppointmentsService } from 'app/controller/appointments.service';
import { Doctor } from '../../models/doctor.model';

@Component({
  selector: 'app-find-doctor',
  templateUrl: './find-doctor.component.html',
  styleUrls: ['./find-doctor.component.css']
})
export class FindDoctorComponent {
  speciality: any;
  constructor(private router: Router,private route: ActivatedRoute, private appointmentService: AppointmentsService) { 

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
searchQuery: string = '';
searchResults: Doctor[] = [];
searchDoctor() {
  console.log("find")
  // Implement your backend search logic here
  // You can send the search query to your backend API
  // For simplicity, let's just log the search query for now
  this.searchResults = this.doctors.filter(doctor =>
    doctor.firstName.toLowerCase().includes(this.searchQuery.toLowerCase())
  );
  console.log(this.searchResults)
  console.log(this.searchQuery)
}
navigateToSpecificUrl(id:any) {
  
  const specificUrl = `/book-appointment/${id}`; // Replace this with your actual URL
  this.router.navigateByUrl(specificUrl);
}
// allDoctors = [
//   { username: 'Dr. John Doe', field: 'Cardiology', finalRating: 4.5 },
//   { username: 'Dr. John Doe', field: 'Cardiology', finalRating: 4.5 },
//   { username: 'Dr. John Doe', field: 'Cardiology', finalRating: 4.5 },
//   { username: 'Dr. John Doe', field: 'Cardiology', finalRating: 4.5 },
//   // ... other doctors
// ]
}
