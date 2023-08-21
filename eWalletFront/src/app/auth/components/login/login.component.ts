import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { map, tap } from 'rxjs';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  loginForm!: FormGroup;
  emailCtrl!: FormControl;
  isLogged = this.authService.isLogged();
  isWrongCredentials = false;
  isLoading = false;
  currentUser = JSON.parse(sessionStorage.getItem("currentUser")!);
  
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

  getFormControlErrorText(ctrl: AbstractControl): string {
    if(ctrl.hasError('required')) {
      return 'Ce champ est requis';
    } else {
      return 'Ce champs requiert une adresse mail valide';
    }
  }

  onLogin(): void {
    this.isLoading = true;
    this.isWrongCredentials = false;
    this.authService.login(this.loginForm.value).pipe(
      tap(logged => {
        this.isLoading = false;
        if (logged) {
          this.loginForm.reset();
          this.router.navigateByUrl('/home');
        } else {
          this.isWrongCredentials = true;
        }
      }),
    ).subscribe();
  }

  onLogout(): void {
    if(this.authService.logout()) {
      this.isLogged=false;
    }
  }
}
