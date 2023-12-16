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

  singup(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/api/v1/auth/register`, data,this.httpOptions);
  }
  login(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/api/v1/auth/authenticate`, data,this.httpOptions);
  }
}
