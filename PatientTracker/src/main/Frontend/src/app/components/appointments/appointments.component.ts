import { Component } from '@angular/core';

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent {
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
