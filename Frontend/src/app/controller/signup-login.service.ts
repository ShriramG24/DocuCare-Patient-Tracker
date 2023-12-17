import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SignupLoginService {
  private apiUrl = 'http://localhost:8080';
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

 // Replace with your backend API URL

  constructor(private http: HttpClient) { }

  getData(): Observable<any> {
    return this.http.get(`${this.apiUrl}/api/data`);
  }

  signupDoctor(data: any): Observable<any> {
    console.log(data);
    return this.http.post(`${this.apiUrl}/api/register/doctor`, data, this.httpOptions);
  }

  signupPatient(data: any): Observable<any> {
    console.log(data);
    return this.http.post(`${this.apiUrl}/api/register/patient`, data, this.httpOptions);
  }

  loginDoctor(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/api/login/doctor`, data, this.httpOptions);
  }

  loginPatient(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/api/login/patient`, data, this.httpOptions);
  }
}
