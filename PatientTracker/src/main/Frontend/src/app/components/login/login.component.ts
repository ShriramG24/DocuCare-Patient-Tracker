import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { AppointmentsService } from 'app/controller/appointments.service';
import { GetPatientDoctorsService } from 'app/controller/get-patient-doctors.service';
import { SignupLoginService } from 'app/controller/signup-login.service';
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
  user = new User();
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
    password: new FormControl(''),
  });
  onSignup() {
    if(this.SignUpForm.get('type')?.value=='patient')
    {

      this.user.type = 'Patient';
      this.user.role = 'Manager';
      this.user.age = this.SignUpForm.get('age')?.value;
      const result = this.SignUpForm.get('name')?.value?.split(" ");
      if (result && result.length >= 2) {
        this.user.firstName = result[0];
        this.user.lastName = result[1];
      }
      const emailValue = this.SignUpForm.get('email')?.value;

      if (emailValue !== null && emailValue !== undefined) {
        this.user.email = emailValue;
      }
      const phone = this.SignUpForm.get('phone')?.value;

      if (phone !== null && phone !== undefined) {
        this.user.phone = phone;
      }
      const gender = this.SignUpForm.get('gender')?.value;

      if (gender !== null && gender !== undefined) {
        this.user.gender = gender;
      }
      const password = this.SignUpForm.get('password')?.value;
      if (password !== null && password !== undefined) {
        this.user.password = password;
      }
     
      //this.user.firstName = this.SignUpForm.get('name')?.value;
    }
    else{
      this.user.type = 'Doctor';
      this.user.role = 'Admin';
      this.user.age = this.SignUpForm.get('age')?.value;
      const result = this.SignUpForm.get('name')?.value?.split(" ");
      if (result && result.length >= 2) {
        this.user.firstName = result[0];
        this.user.lastName = result[1];
      }
      const emailValue = this.SignUpForm.get('email')?.value;

      if (emailValue !== null && emailValue !== undefined) {
        this.user.email = emailValue;
      }
      const phone = this.SignUpForm.get('phone')?.value;

      if (phone !== null && phone !== undefined) {
        this.user.phone = phone;
      }
      const gender = this.SignUpForm.get('gender')?.value;

      if (gender !== null && gender !== undefined) {
        this.user.gender = gender;
      }
      const specialty = this.SignUpForm.get('specialization')?.value;

      if (specialty !== null && specialty !== undefined) {
        this.user.specialty = specialty;
      }
      const address = this.SignUpForm.get('clinicAddress')?.value;

      if (address !== null && address !== undefined) {
        this.user.address = address;
      }
      const experience = this.SignUpForm.get('experience')?.value;

      if (experience !== null && experience !== undefined) {
        this.user.experience = experience;
      }
      const degree = this.SignUpForm.get('degree')?.value;

      if (degree !== null && degree !== undefined) {
        this.user.degree = degree;
      }
      const password = this.SignUpForm.get('password')?.value;

      if (password !== null && password !== undefined) {
        this.user.password = password;
      }
    }
    console.log(this.user);
    this.signup.singup(this.user).subscribe((response) => {
      console.log(response)
    });
    
   // this.go();
  }
  go()
  {
    this.route.navigateByUrl('/dashboard');
  }

  onLogin() {
    // TODO: Use EventEmitter with form value
    console.log(this.LoginForm.value);
    this.go();
  }
}
