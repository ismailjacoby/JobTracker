import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../services/account.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent implements OnInit {
  username: string = '';
  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    const storedUsername = localStorage.getItem('username');
    this.username = storedUsername ? storedUsername : '';
  }

  isLoggedIn(): boolean {
    return this.accountService.isLoggedIn();
  }

  logout() {
    this.accountService.logout();
  }
}
