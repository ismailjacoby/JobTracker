import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { JobStatus } from '../../../models/job-status';
import { JobService } from '../../../services/job.service';
import { Job } from '../../../models/job';

@Component({
  selector: 'app-job-create',
  standalone: false,
  templateUrl: './job-create.component.html',
  styleUrl: './job-create.component.css',
})
export class JobCreateComponent {
  company: string = '';
  title: string = '';
  location: string = '';
  status: JobStatus = JobStatus.APPLIED;
  dateApplied: Date = new Date('2025-02-24');
  link: string = '';

  constructor(private router: Router, private jobService: JobService) {}

  onSubmit() {
    const newJob: Job = {
      id: Date.now(),
      company: this.company,
      title: this.title,
      location: this.location,
      status: this.status,
      dateApplied: this.dateApplied,
      link: this.link,
    };

    this.jobService.addJob(newJob);

    this.router.navigate(['/job/list']);
  }

  cancel() {
    this.router.navigate(['/job/list']);
  }
}
