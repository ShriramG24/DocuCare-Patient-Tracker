import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AppointmentsComponent } from './components/appointments/appointments.component';
import { FindDoctorComponent } from './components/find-doctor/find-doctor.component';
import { BookappointmentComponent } from './components/bookappointment/bookappointment.component';
import { PrescriptionsComponent } from './components/prescriptions/prescriptions.component';
import { ProfileComponent } from './components/profile/profile.component';
const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'appointments',
    component: AppointmentsComponent,
  },
  {
    path: 'find-doctor/:speciality',
    component: FindDoctorComponent,
  },
  {
    path: 'book-appointment/:docid',
    component: BookappointmentComponent,
  },
  {
    path:'dashboard',
    component:DashboardComponent,
  },
  {
    path:'prescriptions/:id',
    component:PrescriptionsComponent,
  },
  {
    path:'profile',
    component: ProfileComponent,
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
