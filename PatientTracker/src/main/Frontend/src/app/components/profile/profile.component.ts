import { Component,OnInit } from '@angular/core';

import { HttpClient } from '@angular/common/http';
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

  constructor(private http: HttpClient) { 
    this.user.type = 'Patient';
  }
  onSubmit() {
    // Handle form submission here
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