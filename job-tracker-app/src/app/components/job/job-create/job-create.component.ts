import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JobService } from '../../../services/job.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-job-create',
  standalone: false,
  templateUrl: './job-create.component.html',
  styleUrl: './job-create.component.css',
})
export class JobCreateComponent implements OnInit {
  jobForm: FormGroup = new FormGroup({});
  errorMessage: string = '';

  constructor(
    private router: Router,
    private jobService: JobService,
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.jobForm = this.formBuilder.group({
      company: ['', Validators.required],
      title: ['', Validators.required],
      location: ['', Validators.required],
      status: ['', Validators.required],
      dateApplied: ['', Validators.required],
      link: ['', Validators.required],
      description: [''],
      salary: [''],
      notes: [''],
    });

    let id = this.activatedRoute.snapshot.paramMap.get('id');
    if (id) {
      let job = this.jobService.getJob(+id).subscribe((job) => {
        if (job) {
          this.jobForm.patchValue(job);
        }
      });
    }
  }

  onSubmit() {
    if (this.jobForm.valid) {
      let id = this.activatedRoute.snapshot.paramMap.get('id');

      if (id) {
        this.jobService.updateJob(+id, this.jobForm.value).subscribe({
          next: () => {
            this.router.navigate(['/job/list']);
          },
          error: (error) => {
            this.errorMessage = error.error.message;
          },
        });
      } else {
        this.jobService.addJob(this.jobForm.value).subscribe({
          next: () => {
            this.router.navigate(['/job/list']);
          },
          error: (error) => {
            console.log(error.error);
            this.errorMessage = error.error.message;
          },
        });
      }
    }
  }

  cancel() {
    this.router.navigate(['/job/list']);
  }
}
