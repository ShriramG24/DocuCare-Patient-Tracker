import { Component } from '@angular/core';

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
}
