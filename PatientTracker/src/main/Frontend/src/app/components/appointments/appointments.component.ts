import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppointmentsService } from 'app/controller/appointments.service';
@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent {
  constructor(private router: Router,private appointmentService: AppointmentsService) { 

  }
  specializations:any;
  ngOnInit(): void {
    // Example: Fetch data from the backend
    
    
    this.appointmentService.getAllSpecialization().subscribe((data) => {
      console.log(data);
      this.specializations = data;
    }, (error) => {
      console.error('Error fetching doctor data', error);
    });
  }
  navigateToSpecificUrl(id:any) {
    const specificUrl = `/find-doctor/${id}`; // Replace this with your actual URL
    this.router.navigateByUrl(specificUrl);
  }
  cards = [
    { title: 'Card 1', text: 'Some text for Card 1.' },
    { title: 'Card 2', text: 'Some text for Card 2.' },
    { title: 'Card 3', text: 'Some text for Card 3.' },
    { title: 'Card 4', text: 'Some text for Card 4.' },
    { title: 'Card 1', text: 'Some text for Card 1.' },
    { title: 'Card 2', text: 'Some text for Card 2.' },
    { title: 'Card 3', text: 'Some text for Card 3.' },
    { title: 'Card 4', text: 'Some text for Card 4.' },
    { title: 'Card 4', text: 'Some text for Card 4.' },
    { title: 'Card 1', text: 'Some text for Card 1.' },
    { title: 'Card 2', text: 'Some text for Card 2.' },
    { title: 'Card 3', text: 'Some text for Card 3.' },

  ];
}
