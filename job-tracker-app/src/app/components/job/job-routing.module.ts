import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { JobListComponent } from './job-list/job-list.component';
import { JobCreateComponent } from './job-create/job-create.component';
import { JobDetailsComponent } from './job-details/job-details.component';
import { isLoggedInGuard } from '../../guards/is-logged-in.guard';

const routes: Routes = [
  { path: '', redirectTo: 'list', pathMatch: 'full' },
  { path: 'list', component: JobListComponent, canActivate: [isLoggedInGuard] },
  {
    path: 'create',
    component: JobCreateComponent,
    canActivate: [isLoggedInGuard],
  },
  {
    path: 'edit/:id',
    component: JobCreateComponent,
    canActivate: [isLoggedInGuard],
  },
  {
    path: 'details/:id',
    component: JobDetailsComponent,
    canActivate: [isLoggedInGuard],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class JobRoutingModule {}
