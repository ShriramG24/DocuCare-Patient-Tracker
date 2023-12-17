import { Patient} from './patient.model';
import { Doctor } from './doctor.model';

export class Prescription  {
    id!: number;
  doctor!: Doctor; // You need to create a Doctor model as well
  patient!: Patient; // You need to create a Patient model as well
  medicines!: string;
  reports!: string;
  instructions!: string;
}