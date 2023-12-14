import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GetPatientDoctorsService {
  private apiUrl = 'http://localhost:8080'; // Replace with your backend API URL

  constructor(private http: HttpClient) { }

  getPatient(patientId:any): Observable<any> {
    
    return this.http.get(`${this.apiUrl}/api/patients/${patientId}`);
  }
  getDoctors(docid:any): Observable<any> {
    
    return this.http.get(`${this.apiUrl}/api/doctors/${docid}`);
  }
 
}
