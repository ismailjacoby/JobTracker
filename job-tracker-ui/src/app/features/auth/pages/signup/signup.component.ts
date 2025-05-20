import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from '../../../../core/services/auth.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent implements OnInit{
  errorMessage = '';
  successMessage = '';

  signupForm: FormGroup = new FormGroup({});

  formBuilder = inject(FormBuilder);
  router = inject(Router);
  authService = inject(AuthService);

  ngOnInit(): void{
    this.signupForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(8)]],
    })
  }

  onSignup(){
    if(this.signupForm.invalid){
      return;
    }

    if (
      this.signupForm.value.password != this.signupForm.value.confirmPassword
    ) {
      this.errorMessage = 'Passwords do not match.';
      return;
    }

    this.authService.signup(this.signupForm.value).subscribe({
      next: (response) => {
        this.signupForm.reset();
        this.successMessage = response;
        this.errorMessage = '';
        setTimeout(() => this.router.navigate(['/auth/login']), 3000)
      },
      error: (error) => {
        console.log(error);
        this.errorMessage = error.error.message || 'An unexpected error occurred. Please try again.';
      }
    })
  }
}
