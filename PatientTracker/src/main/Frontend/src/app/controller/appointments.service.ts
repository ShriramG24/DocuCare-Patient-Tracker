import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';
import { Appointment } from 'app/models/appointment.model';

@Injectable({
  providedIn: 'root'
})

export class AppointmentsService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  private apiUrl = 'http://localhost:8080'; // Replace with your backend API URL

  constructor(private http: HttpClient) { }

  getAllSpecialization(): Observable<any> {
    
    return this.http.get(`${this.apiUrl}/api/doctors/specialties`);
  }

  getDoctorsofSpecialization(specialization:string): Observable<any> {
    
    return this.http.get(`${this.apiUrl}/api/doctors/specialty/${specialization}`);
  }

  bookAppointment(data: Appointment): Observable<any> {
   // return this.http.post<Appointment>(`${this.apiUrl}/api/appointments`, data,{responseType:'text' as 'json'}).pipe();
    return this.http.post(`${this.apiUrl}/api/appointments`,data, this.httpOptions);
  }
  getPatientsAppointments(id : any): Observable<any> {
    
    return this.http.get(`${this.apiUrl}/api/patients/${id}/appointments`);
  }
}
