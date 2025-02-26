import { Validators } from '@angular/forms';
import { UserRoles } from './user-roles';

export interface Auth {
  username: string;
  token: string;
  role: UserRoles;
}

export const LOGIN_FORM = {
  username: ['', Validators.required],
  password: ['', Validators.required],
};
