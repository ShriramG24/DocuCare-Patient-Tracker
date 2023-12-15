import { Component } from '@angular/core';
import { AppointmentsService } from 'app/controller/appointments.service';
import { GetPatientDoctorsService } from 'app/controller/get-patient-doctors.service';
import { Appointment } from 'app/models/appointment.model';
import { Patient } from 'app/models/patient.model';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  patient = new Patient();
  appointments : Appointment[]=[];
  medicalNews: any[] = [];
  activeSlideIndex = 0;
  constructor( private http: HttpClient, private getPatientDoctor: GetPatientDoctorsService , private appointmentService: AppointmentsService) {
    
  }
  user ={type: "Doctor",
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

  ngOnInit(): void {
    // Example: Fetch data from the backend
    
    if(this.user.type==='Patient')
    {
      this.getPatientDoctor.getPatient(1).subscribe((data) => {
        
       
      }, (error) => {
        console.error('Error fetching patient data', error);
      });
      this.appointmentService.getPatientsAppointments(1).subscribe((data) => {
        
        this.appointments = data;
        
        for (let i = 0; i < this.appointments.length; i++) {
          console.log("hello")
          console.log(this.appointments[i]); // Accessing each element in the array
        }
      
      }, (error) => {
        console.error('Error fetching appointments data', error);
      });
      
    }
    else{
      this.appointmentService.getDoctorAppointments(1).subscribe((data) => {
        console.log("hello")
        console.log(data);
        this.appointments = data;
        
        for (let i = 0; i < this.appointments.length; i++) {
          console.log("hello")
          console.log(this.appointments[i]); // Accessing each element in the array
        }
      
      }, (error) => {
        console.error('Error fetching appointments data', error);
      });

    }

    const apiKey = 'c8de7b9db39d597005ce18c7409b7d55';
    const url = `https://gnews.io/api/v4/top-headlines?lang=en&token=${apiKey}&topic=health`;
    this.http.get<any>(url).subscribe(
      (response) => {
        if (response.articles) {
          this.medicalNews = response.articles;
          console.log(this.medicalNews)
        }
      },
      (error) => {
        console.error('Error fetching news:', error);
      }
    );
    setInterval(() => {
      this.nextSlide();
    }, 30000); 
    
}
nextSlide() {
  this.activeSlideIndex = (this.activeSlideIndex + 1) % this.medicalNews.length;
}

prevSlide() {
  this.activeSlideIndex = (this.activeSlideIndex - 1 + this.medicalNews.length) % this.medicalNews.length;
}

news={

}


}
