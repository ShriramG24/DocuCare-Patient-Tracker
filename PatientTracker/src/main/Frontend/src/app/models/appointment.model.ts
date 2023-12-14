import { Doctor } from "./doctor.model";
import { Patient } from "./patient.model";
export class Appointment {
    id!: number;
    doctor!: Doctor; // Assuming you have a Doctor model
    patient!: Patient; // Assuming you have a Patient model
    time!: Date;
    status!: string;
    purpose!: string;
    notes?: string; // Optional property
  }