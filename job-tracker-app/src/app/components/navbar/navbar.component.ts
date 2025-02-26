import { Component } from '@angular/core';
import { AccountService } from '../../services/account.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  constructor(private accountService: AccountService, private router: Router) {}

  isLoggedIn(): boolean {
    return this.accountService.isLoggedIn();
  }

  logout() {
    this.accountService.logout();
  }
}
