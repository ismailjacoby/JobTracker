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
  }

  onSubmit() {
    if (this.jobForm.valid) {
      let job = this.jobForm.value;
      this.jobService.addJob(job);
      this.router.navigate(['/job/list']);
    }
  }

  cancel() {
    this.router.navigate(['/job/list']);
  }
}
