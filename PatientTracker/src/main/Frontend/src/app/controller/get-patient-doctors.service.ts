import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GetPatientDoctorsService {
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('jwtToken')
    })
  };
  private apiUrl = 'http://localhost:8080'; // Replace with your backend API URL

  constructor(private http: HttpClient) { }

  getPatient(patientId:any): Observable<any> {
    const authHeader = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem('jwtToken') });
    return this.http.get(`${this.apiUrl}/api/patients/${patientId}`, { headers: authHeader });
  }
  getDoctors(docid:any): Observable<any> {
    const authHeader = new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem('jwtToken') });
    return this.http.get(`${this.apiUrl}/api/doctors/${docid}`, { headers: authHeader });
  }

  uploadFile(id : any,data :any): Observable<any> {
    return this.http.post(`${this.apiUrl}/api/files/${id}`,data, this.httpOptions);
  }
 
}
