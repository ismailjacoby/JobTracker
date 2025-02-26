import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JobService } from '../../../services/job.service';
import { Job } from '../../../models/job';

@Component({
  selector: 'app-job-details',
  standalone: false,
  templateUrl: './job-details.component.html',
  styleUrl: './job-details.component.css',
})
export class JobDetailsComponent implements OnInit {
  job!: Job;

  constructor(
    private activatedRoute: ActivatedRoute,
    private jobService: JobService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const jobId = Number(this.activatedRoute.snapshot.paramMap.get('id'));

    this.jobService.getJob(jobId).subscribe(
      (data: Job) => {
        this.job = data;
      },
      (error) => {
        console.log('Error fetching job details: ', error);
      }
    );
  }

  deleteJob(id: number) {
    this.jobService.deleteJob(id).subscribe({
      next: () => {
        this.router.navigate(['job/list']);
      },
      error: (err) => {
        console.error('Error deleting job:', err);
      },
    });
  }
}
