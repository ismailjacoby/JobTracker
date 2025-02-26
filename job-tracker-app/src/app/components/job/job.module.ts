import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobRoutingModule } from './job-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { JobCreateComponent } from './job-create/job-create.component';
import { JobListComponent } from './job-list/job-list.component';
import { JobDetailsComponent } from './job-details/job-details.component';

@NgModule({
  declarations: [JobCreateComponent, JobListComponent, JobDetailsComponent],
  imports: [CommonModule, JobRoutingModule, FormsModule, ReactiveFormsModule],
})
export class JobModule {}
