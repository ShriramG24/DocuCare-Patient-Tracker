import { Component } from '@angular/core';
import { AppointmentsService } from 'app/controller/appointments.service';
@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent {
  constructor(private appointmentService: AppointmentsService) { 

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
