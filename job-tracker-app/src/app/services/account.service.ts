import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
import { Auth } from '../models/auth';
import { Router } from '@angular/router';
import { UserRoles } from '../models/user-roles';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private apiUrl = 'http://localhost:8082/account';
  public userRole!: string;

  connectedUser = new BehaviorSubject<Auth | null>(null);

  constructor(private http: HttpClient, private router: Router) {
    const token = localStorage.getItem('token');
    if (token) {
      const role = localStorage.getItem('role');
      if (role) {
        this.userRole = role;
      }
      let roleConnected: UserRoles;
      if (role === 'ADMIN') {
        roleConnected = UserRoles.ADMIN;
      } else {
        roleConnected = UserRoles.USER;
      }
      this.connectedUser.next({
        token: token,
        role: roleConnected,
        username: localStorage.getItem('username') || '',
      });
    }
  }

  login(username: string, password: string): Observable<any> {
    return this.http
      .post<Auth>(this.apiUrl + '/login', { username, password })
      .pipe(
        tap((value) => {
          localStorage.setItem('token', value.token);
          localStorage.setItem('role', value.role.toString());
          this.userRole = value.role.toString();
          localStorage.setItem('username', value.username);
          this.connectedUser.next(value);
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
    this.userRole = '';
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

  getUserRole(): string {
    return this.userRole;
  }
}
