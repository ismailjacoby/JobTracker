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
  searchTerm: string = '';

  jobs: Job[] = [];

  constructor(private jobService: JobService) {}

  ngOnInit(): void {
    this.jobs = this.jobService.getJobs();
  }

  searchJobs() {
    this.jobs = this.jobService.searchJobs(this.searchTerm);
  }
}
