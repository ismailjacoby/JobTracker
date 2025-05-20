import {inject, Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {SignupForm} from '../models/auth.model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`

  http = inject(HttpClient);
  router = inject(Router);

  signup(signupForm: SignupForm): Observable<string>{
    return this.http.post<string>(`${this.apiUrl}/signup`, signupForm,{
      responseType: 'text' as 'json',
    });
  }
}
