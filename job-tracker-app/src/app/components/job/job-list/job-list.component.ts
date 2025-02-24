import { Component } from '@angular/core';
import { Job } from '../../../models/job';

@Component({
  selector: 'app-job-list',
  standalone: false,
  templateUrl: './job-list.component.html',
  styleUrl: './job-list.component.css',
})
export class JobListComponent {
  searchTerm: string = '';

  jobs: Job[] = [];

  addJob() {
    alert('Adds a Job');
  }

  searchJobs() {
    alert('Searching a Job...');
  }
}
