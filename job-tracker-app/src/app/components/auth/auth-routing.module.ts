import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { isLoggedOutGuard } from '../../guards/is-logged-out.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [isLoggedOutGuard] },
  {
    path: 'signup',
    component: SignupComponent,
    canActivate: [isLoggedOutGuard],
  },
  {
    path: 'forgot-password',
    component: ForgotPasswordComponent,
    canActivate: [isLoggedOutGuard],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AuthRoutingModule {}
