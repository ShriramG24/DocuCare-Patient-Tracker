import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { AppointmentsService } from 'app/controller/appointments.service';
import { SignupLoginService } from 'app/controller/signup-login.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  specializations : any;
  constructor(private route:Router,private signupLoginService: SignupLoginService,private appointmentService: AppointmentsService) { 

  }
  ngOnInit(): void {
    // Example: Fetch data from the backend
    this.signupLoginService.getData().subscribe(data => {
      console.log(data);
    });
    this.appointmentService.getAllSpecialization().subscribe((data) => {
      console.log(data);
      this.specializations = data;
    }, (error) => {
      console.error('Error fetching doctor data', error);
    });

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
    name: new FormControl(null,[Validators.required, Validators.minLength(3)]),
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
    // TODO: Use EventEmitter with form value
    console.log(this.SignUpForm.value);
    this.go();
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
