import { Injectable } from '@angular/core';
import { Job } from '../models/job';

@Injectable({
  providedIn: 'root',
})
export class JobService {
  private jobs: Job[] = [];

  constructor() {}

  getJobs(): Job[] {
    return this.jobs;
  }

  addJob(job: Job) {
    this.jobs.push(job);
  }

  searchJobs(searchTerm: string): Job[] {
    return this.jobs.filter(
      (job) =>
        job.company.toLowerCase().includes(searchTerm.toLowerCase()) ||
        job.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        job.location.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }
}
