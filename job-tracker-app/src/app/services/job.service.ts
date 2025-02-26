import { Injectable } from '@angular/core';
import { Job } from '../models/job';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class JobService {
  private jobs: Job[] = [];
  private apiUrl = 'http://localhost:8000/job';

  constructor(private http: HttpClient) {}

  addJob(job: Job) {
    this.jobs.push(job);
  }

  getJob(id: number): Job | undefined {
    return this.jobs.find((job) => job.id === id);
  }

  // Get all Jobs
  getJobs(): Observable<Job[]> {
    return this.http.get<Job[]>(this.apiUrl + '/all');
  }

  updateJob(updatedJob: Job): void {
    let index = this.jobs.findIndex((res) => res.id === updatedJob.id);
    this.jobs[index] = updatedJob;
  }

  deleteJob() {}

  searchJobs(searchTerm: string): Job[] {
    return this.jobs.filter(
      (job) =>
        job.company.toLowerCase().includes(searchTerm.toLowerCase()) ||
        job.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        job.location.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }
}
