import { Component } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AppointmentsService } from 'app/controller/appointments.service';
import { GetPatientDoctorsService } from 'app/controller/get-patient-doctors.service';
import { Doctor } from 'app/models/doctor.model';
import { Patient } from 'app/models/patient.model';
import { Prescription } from 'app/models/prescriptions.model';

@Component({
  selector: 'app-prescriptions',
  templateUrl: './prescriptions.component.html',
  styleUrls: ['./prescriptions.component.css']
})
export class PrescriptionsComponent {
  combinedForms!: FormGroup;
  patient = new Patient();
  doctor = new Doctor();
  prescription = new Prescription();
  date = new Date().toLocaleDateString();
  constructor(private fb: FormBuilder,private route: ActivatedRoute, private appointmentService: AppointmentsService, private getPatientDoctor: GetPatientDoctorsService ) {
    this.initializeForm();
  }
  ngOnInit(): void {
    // Example: Fetch data from the backend
    this.getPatientDoctor.getPatient(1).subscribe((data) => {
      console.log(data);
      this.patient = data;
    }, (error) => {
      console.error('Error fetching patient data', error);
    });

    this.getPatientDoctor.getDoctors(1).subscribe((data) => {
      console.log(data);
      this.patient = data;
    }, (error) => {
      console.error('Error fetching patient data', error);
    });
   
    
}
  private initializeForm() {
    this.combinedForms = this.fb.group({
      medications: this.fb.array([this.createMedicationField()]),
      reports: this.fb.array([this.createReportField()]),
      instructions: this.fb.array([this.createInstructionField()])
    });
  }

  private createMedicationField(): FormControl {
    return this.fb.control('');
  }

  private createReportField(): FormControl {
    return this.fb.control('');
  }

  private createInstructionField(): FormControl {
    return this.fb.control('');
  }

  get medicationsControls() {
    return (this.combinedForms.get('medications') as FormArray).controls as FormControl[];
  }

  get reportsControls() {
    return (this.combinedForms.get('reports') as FormArray).controls as FormControl[];
  }

  get instructionsControls() {
    return (this.combinedForms.get('instructions') as FormArray).controls as FormControl[];
  }

  addMedicationField() {
    const medicationsArray = this.combinedForms.get('medications') as FormArray;
    medicationsArray.push(this.createMedicationField());
  }

  removeMedicationField(index: number) {
    const medicationsArray = this.combinedForms.get('medications') as FormArray;
    medicationsArray.removeAt(index);
  }

  addReportField() {
    const reportsArray = this.combinedForms.get('reports') as FormArray;
    reportsArray.push(this.createReportField());
  }

  removeReportField(index: number) {
    const reportsArray = this.combinedForms.get('reports') as FormArray;
    reportsArray.removeAt(index);
  }

  addInstructionField() {
    const instructionsArray = this.combinedForms.get('instructions') as FormArray;
    instructionsArray.push(this.createInstructionField());
  }

  removeInstructionField(index: number) {
    const instructionsArray = this.combinedForms.get('instructions') as FormArray;
    instructionsArray.removeAt(index);
  }
  checkFormArrays(group: FormGroup) {
    const medications = (group.get('medications') as FormArray).controls.length > 0;
    const reports = (group.get('reports') as FormArray).controls.length > 0;
    const instructions = (group.get('instructions') as FormArray).controls.length > 0;

    return medications || reports || instructions ? null : { noEntries: true };
  }
  prescribe() {
    this.prescription.doctor = this.doctor;
    this.prescription.patient = this.patient;
    this.prescription.medicines = this.combinedForms.get('medications')?.value[0];
    this.prescription.instructions = this.combinedForms.get('instructions')?.value[0];
    this.prescription.reports = this.combinedForms.get('reports')?.value[0];
    this.appointmentService.prescribe(this.prescription).subscribe();
    
    console.log('Prescription submitted:', this.combinedForms.value);
    
  }
}
