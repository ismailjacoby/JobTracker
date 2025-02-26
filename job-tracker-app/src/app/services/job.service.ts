import { Injectable } from '@angular/core';
import { Job } from '../models/job';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class JobService {
  private jobs: Job[] = [];
  private apiUrl = 'http://localhost:8080/job';
  private headers = new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: `${localStorage.getItem('token')}`,
  });

  constructor(private http: HttpClient) {}

  // Add Job (sends authenticated request)
  addJob(job: Job): Observable<any> {
    return this.http.post(`${this.apiUrl}/add`, job, { headers: this.headers });
  }

  // Get a single job by ID
  getJob(id: number): Observable<any> {
    return this.http.get<Job>(`${this.apiUrl}/${id}`, {
      headers: this.headers,
    });
  }

  // Get all jobs for the authenticated user
  getJobs(): Observable<Job[]> {
    return this.http.get<Job[]>(`${this.apiUrl}/all`, {
      headers: this.headers,
    });
  }

  // Update Job
  updateJob(id: number, updatedJob: Job): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, updatedJob, {
      headers: this.headers,
    });
  }

  // Delete Job
  deleteJob(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, { headers: this.headers });
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
