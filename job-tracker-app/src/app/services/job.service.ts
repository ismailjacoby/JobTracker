import { Injectable } from '@angular/core';
import { Job } from '../models/job';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class JobService {
  private apiUrl = `${environment.baseUrl}/job`;

  constructor(private http: HttpClient) {}

  // Add Job (sends authenticated request)
  addJob(job: Job): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/add`, job);
  }

  // Get a single job by ID
  getJob(id: number): Observable<Job> {
    return this.http.get<Job>(`${this.apiUrl}/${id}`);
  }

  // Get all jobs for the authenticated user
  getJobs(): Observable<Job[]> {
    return this.http.get<Job[]>(`${this.apiUrl}/all`);
  }

  // Update Job
  updateJob(id: number, updatedJob: Job): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}`, updatedJob);
  }

  // Delete Job
  deleteJob(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
