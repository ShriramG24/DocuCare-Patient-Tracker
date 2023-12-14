import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SignupLoginService {
  private apiUrl = 'http://localhost:3000'; // Replace with your backend API URL

  constructor(private http: HttpClient) { }

  getData(): Observable<any> {
    return this.http.get(`${this.apiUrl}/api/data`);
  }

  postData(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/api/data`, data);
  }
}
