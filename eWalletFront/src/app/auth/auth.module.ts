import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthRoutingModule } from './auth-routing.module';
import { SharedModule } from '../shared/shared.module';
import { AuthService } from './services/auth.service';
import { LoginComponent } from './components/login/login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NewAccountComponent } from './components/new-account/new-account.component';
import { NewAccountService } from './services/new-account.service';

@NgModule({
  declarations: [
    LoginComponent,
    NewAccountComponent
  ],
  imports: [
    AuthRoutingModule,
    CommonModule,
    SharedModule,
    ReactiveFormsModule
  ],
  providers: [
    AuthService,
    NewAccountService
  ]
})
export class AuthModule { }
