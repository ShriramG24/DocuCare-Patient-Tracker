import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { AppointmentsService } from 'app/controller/appointments.service';
import { GetPatientDoctorsService } from 'app/controller/get-patient-doctors.service';
import { SignupLoginService } from 'app/controller/signup-login.service';
import { DoctorRegistration } from 'app/models/doctor-registration.model';
import { Doctor } from 'app/models/doctor.model';
import { PatientRegistration } from 'app/models/patient-registration.model';
import { Patient } from 'app/models/patient.model';
import { User } from 'app/models/user.model';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  specializations = ["Cardiologist", "Dermatologist", "Endocrinologist", "Gastroenterologist",
  "Gynecologist", "Hematologist", "Neurologist", "Oncologist", "Ophthalmologist", "Pediatrician",
  "Psychiatrist", "Pulmonologist", "Radiologist", "Rheumatologist", "Urologist"];
  patientReg = new PatientRegistration();
  doctorReg = new DoctorRegistration();
  constructor(private signup:SignupLoginService,private userDoctor:GetPatientDoctorsService,private route:Router,private signupLoginService: SignupLoginService,private appointmentService: AppointmentsService) { 

  }
  ngOnInit(): void {
    // Example: Fetch data from the backend
  
    

    // const postData = { name: 'John', age: 25 };
    // this.signupLoginService.postData(postData).subscribe(response => {
    //   console.log(response);
    // });
  }
  method = 'signup';

  setMethod(method_change: string)
  {
    this.method = method_change
    console.log(this.method)
  }
  SignUpForm = new FormGroup({
    name: new FormControl(''),
    type:new FormControl(''),
    gender: new FormControl(''),
    email: new FormControl(''),
    contact : new FormControl(),
    age: new FormControl(),
    password: new FormControl(''),
    confirmPassword: new FormControl(''),
    degree: new FormControl(''),
    clinicAddress: new FormControl(''),
    specialization: new FormControl(''),
    experience: new FormControl()
  });
  LoginForm = new FormGroup({
    email: new FormControl(''),
    type:new FormControl(''),
    password: new FormControl(''),
  });
  onSignup() {
    this.patientReg.patient = new Patient();
    if(this.SignUpForm.get('type')?.value=='patient')
    {
      this.patientReg.patient.age = this.SignUpForm.get('age')?.value;
      const result = this.SignUpForm.get('name')?.value?.split(" ");
      if (result && result.length >= 2) {
        this.patientReg.patient.firstName = result[0];
        this.patientReg.patient.lastName = result[1];
      }
      const emailValue = this.SignUpForm.get('email')?.value;

      if (emailValue !== null && emailValue !== undefined) {
        this.patientReg.patient.email = emailValue;
        this.patientReg.email = emailValue;
      }
      const phone = this.SignUpForm.get('contact')?.value;

      if (phone !== null && phone !== undefined) {
        this.patientReg.patient.phone = phone;
      }
      const gender = this.SignUpForm.get('gender')?.value;

      if (gender !== null && gender !== undefined) {
        this.patientReg.patient.gender = gender;
      }
      const password = this.SignUpForm.get('password')?.value;
      if (password !== null && password !== undefined) {
        this.patientReg.patient.password = password;
        this.patientReg.password = password;
      }
      this.signup.signupPatient(this.patientReg).subscribe((response) => {
        alert(response.message);
        if (!response.message.includes("successfully")) {
          this.SignUpForm.reset();
        }
      });
    }
    else{
      this.doctorReg.doctor = new Doctor();
      this.doctorReg.doctor.age = this.SignUpForm.get('age')?.value;
      const result = this.SignUpForm.get('name')?.value?.split(" ");
      if (result && result.length >= 2) {
        this.doctorReg.doctor.firstName = result[0];
        this.doctorReg.doctor.lastName = result[1];
      }
      const emailValue = this.SignUpForm.get('email')?.value;

      if (emailValue !== null && emailValue !== undefined) {
        this.doctorReg.doctor.email = emailValue;
        this.doctorReg.email = emailValue;
      }
      const phone = this.SignUpForm.get('contact')?.value;

      if (phone !== null && phone !== undefined) {
        this.doctorReg.doctor.phone = phone;
      }
      const gender = this.SignUpForm.get('gender')?.value;

      if (gender !== null && gender !== undefined) {
        this.doctorReg.doctor.gender = gender;
      }
      const specialty = this.SignUpForm.get('specialization')?.value;

      if (specialty !== null && specialty !== undefined) {
        this.doctorReg.doctor.specialty = specialty;
      }
      const address = this.SignUpForm.get('clinicAddress')?.value;

      if (address !== null && address !== undefined) {
        this.doctorReg.doctor.clinicAddr = address;
      }
      const experience = this.SignUpForm.get('experience')?.value;

      if (experience !== null && experience !== undefined) {
        this.doctorReg.doctor.experience = experience;
      }
      const degree = this.SignUpForm.get('degree')?.value;

      if (degree !== null && degree !== undefined) {
        this.doctorReg.doctor.degree = degree;
      }
      const password = this.SignUpForm.get('password')?.value;

      if (password !== null && password !== undefined) {
        this.doctorReg.doctor.password = password;
        this.doctorReg.password = password;
      }
      this.signup.signupDoctor(this.doctorReg).subscribe((response) => {
        alert(response.message);
        if (!response.message.includes("successfully")) {
          this.SignUpForm.reset();
        }
      });
    }
    
   // this.go();
  }
  go()
  {
    this.route.navigateByUrl('/dashboard');
  }

  onLogin() {
    // TODO: Use EventEmitter with form value
    const email = this.LoginForm.get('email')?.value;
    const password = this.LoginForm.get('password')?.value;
    const type = this.LoginForm.get('type')?.value;
    if (type == 'doctor') {
      this.signupLoginService.loginDoctor({email, password}).subscribe((response) => {
        const token = response.token;
        if (token) {
          localStorage.setItem('jwtToken', token);
          localStorage.setItem('doctorId', response.id);
          this.go();
        } else {
          alert(response.message);
          this.LoginForm.reset();
        }
      });
    } else {
      this.signupLoginService.loginPatient({email, password}).subscribe((response) => {
        const token = response.token;
        if (token) {
          localStorage.setItem('jwtToken', token);
          localStorage.setItem('patientId', response.id);
          this.go();
        } else {
          alert(response.message);
          this.LoginForm.reset();
        }
      });
    }
  }
}
