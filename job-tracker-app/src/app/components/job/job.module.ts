import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobRoutingModule } from './job-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { JobCreateComponent } from './job-create/job-create.component';
import { JobListComponent } from './job-list/job-list.component';

@NgModule({
  declarations: [JobCreateComponent, JobListComponent],
  imports: [CommonModule, JobRoutingModule, FormsModule, ReactiveFormsModule],
})
export class JobModule {}
