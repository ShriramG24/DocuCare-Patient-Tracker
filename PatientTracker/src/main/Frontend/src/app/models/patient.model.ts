import { Doctor } from './doctor.model'; // Assuming you have a Doctor model
import { Appointment } from './appointment.model';
export class Patient{
    id!: number;
    firstName!: string;
    lastName!: string;
    gender!: string;
    age!: number;
    email!: string;
    phone!: string;
  address!: string;
  allergies?: string;
  medications?: string;
  diagnoses?: string;
  doctors?: Doctor[];
  appointments?: Appointment[];
  files?: File[];
}