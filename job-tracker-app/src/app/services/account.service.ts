import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
import { Auth } from '../models/auth';
import { Router } from '@angular/router';
import { UserRoles } from '../models/user-roles';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private apiUrl = `${environment.baseUrl}/account`;
  public userRole: UserRoles | null = null;

  connectedUser = new BehaviorSubject<Auth | null>(null);

  constructor(private http: HttpClient, private router: Router) {
    const token = localStorage.getItem('token');
    if (token) {
      this.setUserRole(localStorage.getItem('role'));
      this.connectedUser.next(this.getConnectedUser(token));
    }
  }

  private setUserRole(role: string | null) {
    this.userRole = role === 'ADMIN' ? UserRoles.ADMIN : UserRoles.USER;
  }

  private getConnectedUser(token: string): Auth {
    return {
      token: token,
      role: this.userRole || UserRoles.USER,
      username: localStorage.getItem('username') || '',
    };
  }

  login(username: string, password: string): Observable<any> {
    return this.http
      .post<Auth>(this.apiUrl + '/login', { username, password })
      .pipe(
        tap((value) => {
          localStorage.setItem('token', value.token);
          localStorage.setItem('role', value.role.toString());
          localStorage.setItem('username', value.username);
          this.connectedUser.next(value);
          this.setUserRole(value.role.toString());
        }),
        catchError((error) => {
          console.error('Login failed:', error);
          return throwError(() => error);
        })
      );
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    this.userRole = null;
    this.connectedUser.next(null);
    this.router.navigate(['/auth/login']);
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  isLoggedIn() {
    return !!localStorage.getItem('token');
  }

  isAdmin() {
    return localStorage.getItem('role') === 'ADMIN';
  }

  isUser() {
    return localStorage.getItem('role') === 'USER';
  }

  getUserRole(): UserRoles | null {
    return this.userRole;
  }
}
