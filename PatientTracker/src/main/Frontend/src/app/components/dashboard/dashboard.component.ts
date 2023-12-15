import { Component } from '@angular/core';
import { GetPatientDoctorsService } from 'app/controller/get-patient-doctors.service';
import { Patient } from 'app/models/patient.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  patient = new Patient();
  constructor( private getPatientDoctor: GetPatientDoctorsService ) {
    
  }
  ngOnInit(): void {
    // Example: Fetch data from the backend
    this.getPatientDoctor.getPatient(1).subscribe((data) => {
      console.log(data);
      this.user = data;
    }, (error) => {
      console.error('Error fetching patient data', error);
    });
   
    
}
user ={type: "Patient",
       firstName:"Allah",
       lastName:"Allah",
       consultation: [ 1 ],
      medicine: ["bp"],
      tests: ["Blood test"],
      instructions: ["Avoid Sugar"],
    finalRating:" 0.0",
    numberOfAppointments:"0",
  appointment_dates:[1],
booking_date:[1],
time: [1] ,
show_vc: [1]}

news={

}

}
