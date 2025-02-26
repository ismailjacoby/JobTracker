import { Component, OnInit } from '@angular/core';
import { Job } from '../../../models/job';
import { JobStatus } from '../../../models/job-status';
import { JobService } from '../../../services/job.service';
import { AccountService } from '../../../services/account.service';

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

  constructor(
    private jobService: JobService,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    const username = this.accountService.getUsername();

    if (username) {
      this.getJobs();
    }
  }

  getJobs() {
    this.jobService.getJobs().subscribe((jobs) => {
      this.jobs = jobs;
      this.updateStatusCounts();
    });
  }

  deleteJob(id: number) {
    console.log(id);
    this.jobService.deleteJob(id).subscribe({
      next: () => {
        this.getJobs();
      },
      error: (err) => {
        console.error('Error deleting job:', err);
      },
    });
  }

  updateStatusCounts() {
    this.totalApplications = this.jobs.length;

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
      // Check if the job status is a valid enum value
      if (Object.values(JobStatus).includes(job.status as JobStatus)) {
        this.statusCounts[job.status as JobStatus]++;
      } else {
        console.warn(`Invalid status: ${job.status}`);
      }
    });

    console.log('Updated status counts:', this.statusCounts);
  }
}
