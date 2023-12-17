import { Patient} from './patient.model';
import { Appointment } from './appointment.model';

export class Doctor  {
    id!: number;
    firstName!: string;
    lastName!: string;
    gender!: string;
    age!: number;
    email!: string;
    phone!: string;
    password!: string;
    degree?: string;
    specialty?: string;
    clinicAddr?: string;
    experience?: number;
    rating?: number;
    patients?: Patient[];
    appointments?: Appointment[];
    files?: File[];
}