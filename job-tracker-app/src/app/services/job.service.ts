import { Injectable } from '@angular/core';
import { Job } from '../models/job';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class JobService {
  private apiUrl = 'http://localhost:8082/job';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `${localStorage.getItem('token')}`, // Always fetch fresh token
    });
  }

  // Add Job (sends authenticated request)
  addJob(job: Job): Observable<any> {
    return this.http.post(`${this.apiUrl}/add`, job, {
      headers: this.getHeaders(),
    });
  }

  // Get a single job by ID
  getJob(id: number): Observable<any> {
    return this.http.get<Job>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders(),
    });
  }

  // Get all jobs for the authenticated user
  getJobs(): Observable<Job[]> {
    return this.http.get<Job[]>(`${this.apiUrl}/all`, {
      headers: this.getHeaders(),
    });
  }

  // Update Job
  updateJob(id: number, updatedJob: Job): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, updatedJob, {
      headers: this.getHeaders(),
    });
  }

  // Delete Job
  deleteJob(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders(),
    });
  }
}
