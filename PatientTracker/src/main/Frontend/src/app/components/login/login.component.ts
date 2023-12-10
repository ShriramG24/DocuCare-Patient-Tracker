import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
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
  onSignup() {
    // TODO: Use EventEmitter with form value
    console.warn(this.SignUpForm.value.age);
  }
}
