import { Component,OnInit } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GetPatientDoctorsService } from 'app/controller/get-patient-doctors.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  user = {
    type: 'Patient', // Or 'Doctor' based on your user type
    username: 'Allah',
    firstName:'hello',
    lastName:'check',
    age: 0,
    gender:0,
    medications:"tf",
    allergies:'f',
    address:"raand",
    Specialization:"raandAgain",
    qualification:"land"
     // Username or name of the user
    // Other properties...
  };
  medicalNews: any[] = [];
  userForm: FormGroup;

  constructor(private http: HttpClient, private fb: FormBuilder, private userDoctorService: GetPatientDoctorsService) {
    this.user.type = 'Patient';
    // Initialize the form with default values and validators
    this.userForm = this.fb.group({
      firstName: '',
      lastName: [''],
      age: [null],
      gender: [null],
      medications: [null], // No validation for medications
      allergies: [null], // No validation for allergies
      fileUpload: [null], // No validation for file upload
      clinicAddress: [null], // No validation for clinic address
      specialization: [null], // No validation for specialization
      qualification: [null], // No validation for qualification
    });
  }
 
  onSubmit() {
    const file = this.userForm.get('fileUpload')?.value;
    // Handle form submission here
   // this.userDoctorService.uploadFile(1,data).subscribe((data) => {console.log("file uploaded");console.log(data)});
   
      var fd = new FormData();
      fd.append('file', file);
      const headers = new HttpHeaders();
    headers.append('Content-Type', 'undefined');
      this.http.post(`http://localhost:8080/api/files/1`, fd, 
      { headers }
      )
      .subscribe((response) => {
        console.log(response)
      });
    
  
    console.log('Form submitted:', this.user);
    // You can add logic here to send the data to a server or perform other actions
  }
  onFileSelected(event: any) {
    // Handle file selection here
    const file: File = event.target.files[0];
    console.log('File selected:', file);
    // You can add logic here to process the selected file
  }


  ngOnInit(): void {
    
    // Fetch medical news data from News API
    const apiKey = 'c8de7b9db39d597005ce18c7409b7d55';
    const url = `https://gnews.io/api/v4/top-headlines?lang=en&token=${apiKey}&topic=health`;
    this.http.get<any>(url).subscribe(
      (response) => {
        if (response.articles) {
          this.medicalNews = response.articles;
        }
      },
      (error) => {
        console.error('Error fetching news:', error);
      }
    );
  }
}