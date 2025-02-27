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
  isLoggedIn: boolean = false;

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit(): void {
    this.accountService.connectedUser.subscribe((user) => {
      this.isLoggedIn = !!user;
      this.username = user ? user.username : '';
    });

    const storedUsername = localStorage.getItem('username');
    this.username = storedUsername ? storedUsername : '';
  }

  logout() {
    this.accountService.logout();
  }
}
