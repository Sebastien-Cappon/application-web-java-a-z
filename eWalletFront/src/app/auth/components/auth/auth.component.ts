import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Observable, delay, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/app/environments/environment';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent {
  loading = false;
  loginForm!: FormGroup;
  emailCtrl!: FormControl;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.initLoginFormControls();
    this.initLoginForm();
  }

  private initLoginFormControls(): void {
    this.emailCtrl = this.formBuilder.control('', [Validators.required, Validators.email, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]);
  }

  private initLoginForm(): void {
    this.loginForm = this.formBuilder.group({
      email: this.emailCtrl,
      password: ['', Validators.required]
    });
  }

  getFormControlErrorText(ctrl: AbstractControl) {
    if(ctrl.hasError('required')) {
      return 'Ce champ est requis';
    } else {
      return 'Ce champs requiert une adresse mail valide';
    }
  }

  onLogin() {
      this.authService.setToken();
      this.router.navigateByUrl('/home');
  }
}
