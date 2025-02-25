import { Injectable } from '@angular/core';
import { Job } from '../models/job';

@Injectable({
  providedIn: 'root',
})
export class JobService {
  private jobs: Job[] = [];

  constructor() {
    let savedJobs = localStorage.getItem('jobs');
    this.jobs = savedJobs ? JSON.parse(savedJobs) : [];
  }

  addJob(job: Job) {
    this.jobs.push(job);
    localStorage.setItem('jobs', JSON.stringify(this.jobs));
  }

  getJob(id: number): Job | undefined {
    return this.jobs.find((job) => job.id === id);
  }

  getJobs(): Job[] {
    return this.jobs;
  }

  updateJob(updatedJob: Job): void {
    let index = this.jobs.findIndex((res) => res.id === updatedJob.id);
    this.jobs[index] = updatedJob;
    localStorage.setItem('reservations', JSON.stringify(this.jobs));
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
