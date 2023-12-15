import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppointmentsService } from 'app/controller/appointments.service';
import { Patient } from 'app/models/patient.model';
import { GetPatientDoctorsService } from 'app/controller/get-patient-doctors.service';
import { Doctor } from 'app/models/doctor.model';
import { Appointment } from 'app/models/appointment.model';
import { FormControl, FormGroup } from '@angular/forms';
@Component({
  selector: 'app-bookappointment',
  templateUrl: './bookappointment.component.html',
  styleUrls: ['./bookappointment.component.css']
})
export class BookappointmentComponent {
  constructor(private route: ActivatedRoute, private appointmentService: AppointmentsService, private getPatientDoctor: GetPatientDoctorsService) { 

  }
  docid:any;
  patient = new Patient();
  doctor = new Doctor();

  appointment = new Appointment();
  
  ngOnInit(): void {
    // Example: Fetch data from the backend
    
    this.route.params.subscribe(params => {
      this.docid = params['docid'];
    });
    this.getPatientDoctor.getDoctors(this.docid).subscribe((data) => {
      console.log(data);
      this.doctor = data;
    }, (error) => {
      console.error('Error fetching doctor data', error);
    });
    this.getPatientDoctor.getPatient(1).subscribe((data) => {
      console.log(data);
      this.patient = data;
    }, (error) => {
      console.error('Error fetching patient data', error);
    });
   
    
}
BookAppointmentForm = new FormGroup({
  


});
bookAppointment()
{
  
  this.appointment.doctor = this.doctor;
  this.appointment.patient = this.patient;
  this.appointment.status = "Scheduled";
  this.appointment.purpose = "SICK";
  this.appointment.time = new Date().toISOString().slice(0, 19).replace("T", " ");
  this.appointmentService.bookAppointment(this.appointment).subscribe();
    
}
  
}
