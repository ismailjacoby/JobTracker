import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AccountService } from '../services/account.service';

export const isLoggedOutGuard: CanActivateFn = (route, state) => {
  const accountService = inject(AccountService);
  const _route = inject(Router);

  if (!accountService.isLoggedIn()) {
    return true;
  } else {
    return false;
  }
};
