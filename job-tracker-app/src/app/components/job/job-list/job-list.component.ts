import { Component, OnInit } from '@angular/core';
import { Job } from '../../../models/job';
import { JobStatus } from '../../../models/job-status';
import { JobService } from '../../../services/job.service';

@Component({
  selector: 'app-job-list',
  standalone: false,
  templateUrl: './job-list.component.html',
  styleUrl: './job-list.component.css',
})
export class JobListComponent implements OnInit {
  jobs: Job[] = [];
  searchTerm: string = '';
  totalApplications: number = 0;
  statusCounts: Record<JobStatus, number> = {
    [JobStatus.APPLIED]: 0,
    [JobStatus.INTERVIEW_SCHEDULED]: 0,
    [JobStatus.INTERVIEWED]: 0,
    [JobStatus.OFFER_RECEIVED]: 0,
    [JobStatus.ACCEPTED]: 0,
    [JobStatus.REJECTED]: 0,
    [JobStatus.WITHDRAWN]: 0,
    [JobStatus.NO_RESPONSE]: 0,
  };

  JobStatus = JobStatus;

  constructor(private jobService: JobService) {}

  ngOnInit(): void {
    this.jobs = this.jobService.getJobs();
    this.updateStatusCounts();
  }

  searchJobs() {
    this.jobs = this.jobService.searchJobs(this.searchTerm);
  }

  updateStatusCounts() {
    this.totalApplications = this.jobs.length;
    // Reset status counts
    this.statusCounts = {
      [JobStatus.APPLIED]: 0,
      [JobStatus.INTERVIEW_SCHEDULED]: 0,
      [JobStatus.INTERVIEWED]: 0,
      [JobStatus.OFFER_RECEIVED]: 0,
      [JobStatus.ACCEPTED]: 0,
      [JobStatus.REJECTED]: 0,
      [JobStatus.WITHDRAWN]: 0,
      [JobStatus.NO_RESPONSE]: 0,
    };

    this.jobs.forEach((job) => {
      const status = job.status;
      if (this.statusCounts[status] !== undefined) {
        this.statusCounts[status]++;
      }
    });
  }
}
