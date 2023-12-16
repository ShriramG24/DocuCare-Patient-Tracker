import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppointmentsService } from 'app/controller/appointments.service';
import { Patient } from 'app/models/patient.model';
import { GetPatientDoctorsService } from 'app/controller/get-patient-doctors.service';
import { Doctor } from 'app/models/doctor.model';
import { Appointment } from 'app/models/appointment.model';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-bookappointment',
  templateUrl: './bookappointment.component.html',
  styleUrls: ['./bookappointment.component.css']
})
export class BookappointmentComponent {
  constructor(private router: Router,private formBuilder: FormBuilder,private route: ActivatedRoute, private appointmentService: AppointmentsService, private getPatientDoctor: GetPatientDoctorsService) { 

  }
  docid:any;
  patient = new Patient();
  doctor = new Doctor();

  appointment = new Appointment();
  appointmentForm!: FormGroup;

  
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
    this.appointmentForm = this.formBuilder.group({
      reason: ['', Validators.required],
      notes: [''],
      date: ['', Validators.required],
      time: ['', Validators.required]
    });
   
    
}
BookAppointmentForm = new FormGroup({
  


});
submitForm()
{
   const date = this.appointmentForm.get('date')?.value;
      const time = this.appointmentForm.get('time')?.value;
      const reason = this.appointmentForm.get('reason')?.value;
      const notes = this.appointmentForm.get('notes')?.value;
      this.appointment.doctor = this.doctor;
  this.appointment.patient = this.patient;
  this.appointment.status = "Scheduled";
  this.appointment.purpose = reason;
  const dateTimeString = `${date} ${time}`;
  this.appointment.time = new Date(dateTimeString).toISOString().slice(0, 19).replace("T", " ");
  this.appointmentService.bookAppointment(this.appointment).subscribe();
  this.go();
      // Combine date and time to create a DateTime object
      
}
go()
{
  this.router.navigateByUrl('/dashboard');
}
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
