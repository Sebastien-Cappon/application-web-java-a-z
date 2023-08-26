import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, map, tap } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { User } from 'src/app/core/models/user.model';

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
  loginEmailCtrl!: FormControl;

  isLogged = this.authService.isLogged();
  isWrongCredentials = false;
  isLoading = false;

  private currentUserId = Number(sessionStorage.getItem('currentUserId'));
  currentUser$!: Observable<User>;
  
  ngOnInit(): void {
    this.currentUser$ = this.authService.getUserById(this.currentUserId);
    this.initLoginFormControls();
    this.initLoginForm();
  }

  private initLoginFormControls(): void {
    this.loginEmailCtrl = this.formBuilder.control('', [Validators.required, Validators.email, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]);
  }

  private initLoginForm(): void {
    this.loginForm = this.formBuilder.group({
      email: this.loginEmailCtrl,
      password: ['', Validators.required]
    });
  }

  getLoginFormControlErrorText(ctrl: AbstractControl): string {
    if(ctrl.hasError('required')) {
      return 'This input field is required.';
    } else {
      return 'This input filed require an valid email address.';
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
      })
    ).subscribe();
  }

  onLogout(): void {
    if(this.authService.logout()) {
      this.isLogged=false;
    }
  }
}
