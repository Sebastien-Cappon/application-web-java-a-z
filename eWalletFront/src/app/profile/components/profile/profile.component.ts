import { Component } from '@angular/core';
import { ProfileService } from '../../services/profile.service';
import { User } from 'src/app/core/models/user.model';
import { Observable, map, tap } from 'rxjs';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { confirmEqualsValidator } from 'src/app/shared/validators/confirmEquals.validator';
import { emailPatternValidator } from 'src/app/shared/validators/emailPattern.validator';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {

  constructor(
    private formBuilder: FormBuilder,
    private profileService: ProfileService,
    private router: Router
  ) { }

  profileForm!: FormGroup;
  profileFirstnameCtrl!: FormControl;
  profileLastnameCtrl!: FormControl;

  profileEmailForm!: FormGroup;
  profileEmailCtrl!: FormControl;
  profileConfirmEmailCtrl!: FormControl;
  showProfileConfirmEmail$!: Observable<boolean>;
  showProfileEmailError$!: Observable<boolean>;

  profilePasswordForm!: FormGroup;
  profilePasswordCtrl!: FormControl;
  profileConfirmPasswordCtrl!: FormControl;
  showProfileConfirmPassword$!: Observable<boolean>;
  showProfilePasswordError$!: Observable<boolean>;

  isLoading = false;
  isDeleting = false;
  isConfirmingDeletion = false;
  isEditing = false;

  private currentUserId = Number(sessionStorage.getItem('currentUserId'));
  currentUser$!: Observable<User>;
  
  ngOnInit(): void {
    this.initObservables();
    this.initProfileFormControls();
    this.initProfileForm();
    this.initProfileFormObservables();
  }

  private initObservables() {
    this.currentUser$ = this.profileService.getUserById(this.currentUserId);
  }

  private initProfileFormControls(): void {
    this.profileFirstnameCtrl = this.formBuilder.control('');
    this.profileLastnameCtrl = this.formBuilder.control('');
    this.initProfileEmailFormControl();
    this.initProfilePasswordFormControl();
  }

  private initProfileForm(): void {
    this.profileForm = this.formBuilder.group({
      firstname: this.profileFirstnameCtrl,
      lastname: this.profileLastnameCtrl,
      email: this.profileEmailForm.get('email'),
      password: this.profilePasswordForm.get('password')
    })
  }

  private initProfileEmailFormControl() {
    this.profileEmailCtrl = this.formBuilder.control('', [Validators.email, emailPatternValidator()]);
    this.profileConfirmEmailCtrl = this.formBuilder.control('');
    this.profileEmailForm = this.formBuilder.group({
      email: this.profileEmailCtrl,
      confirmEmail: this.profileConfirmEmailCtrl
    }, {
      validators: [confirmEqualsValidator('email', 'confirmEmail')]
    });
  }

  private initProfilePasswordFormControl() {
    this.profilePasswordCtrl = this.formBuilder.control('');
    this.profileConfirmPasswordCtrl = this.formBuilder.control('');
    this.profilePasswordForm = this.formBuilder.group({
      password: this.profilePasswordCtrl,
      confirmPassword: this.profileConfirmPasswordCtrl
    }, {
      validators: [confirmEqualsValidator('password', 'confirmPassword')]
    });
  }

  private initProfileFormObservables() {
    this.initProfileEmailFormObservables();
    this.initProfilePasswordFormObservables();
  }

  private initProfileEmailFormObservables() {
    this.showProfileConfirmEmail$ = this.profileEmailCtrl.valueChanges.pipe(
      map(() => this.profileEmailCtrl.value),
      tap(showProfileConfirmEmail => this.setProfileEmailValidators(showProfileConfirmEmail))
    );
    this.showProfileEmailError$ = this.profileEmailForm.statusChanges.pipe(
      map(status => status === ('INVALID')
        && this.profileEmailCtrl.value
        && this.profileEmailCtrl.valid
        && this.profileConfirmEmailCtrl.value
        && this.profileConfirmEmailCtrl.valid
      )
    );
  }

  private initProfilePasswordFormObservables() {
    this.showProfileConfirmPassword$ = this.profilePasswordCtrl.valueChanges.pipe(
      map(() => this.profilePasswordCtrl.value),
      tap(showProfileConfirmPassword => this.setProfilePasswordValidators(showProfileConfirmPassword))
    );
    this.showProfilePasswordError$ = this.profilePasswordForm.statusChanges.pipe(
      map(status => status === ('INVALID')
        && this.profilePasswordCtrl.value
        && this.profileConfirmPasswordCtrl.value
        && this.profileConfirmPasswordCtrl.valid
      )
    );
  }

  private setProfileEmailValidators(showProfileConfirmEmail: boolean) {
    if (showProfileConfirmEmail) {
      this.profileConfirmEmailCtrl.addValidators([
        Validators.required,
        Validators.email,
        emailPatternValidator()
      ]);
      this.profileConfirmEmailCtrl.markAsTouched();
    } else {
      this.profileConfirmEmailCtrl.reset('');
      this.profileConfirmEmailCtrl.clearValidators();
    }
    this.profileConfirmEmailCtrl.updateValueAndValidity();
  }

  private setProfilePasswordValidators(showProfileConfirmPassword: boolean) {
    if (showProfileConfirmPassword) {
      this.profileConfirmPasswordCtrl.addValidators([Validators.required]);
      this.profileConfirmPasswordCtrl.markAsTouched();
    } else {
      this.profileConfirmPasswordCtrl.reset('');
      this.profileConfirmPasswordCtrl.clearValidators();
    }
    this.profileConfirmPasswordCtrl.updateValueAndValidity();
  }

  getProfileFormControlErrorText(ctrl: AbstractControl): string {
    if(ctrl.hasError('required')) {
      return 'You must confirm your previous input.';
    } else if (ctrl.hasError('email') || ctrl.hasError('emailPatternValidator')){
      return 'This input field require a valid email address.'
    } else {
      return 'An error has occured.';
    }
  }

  onDelete() {
    this.isDeleting = true;
  }

  onConfirmDelete() {
    this.isDeleting = false;
    this.isConfirmingDeletion = true;
  }

  onCompleteDelete() {
    this.isConfirmingDeletion = false;
    this.isLoading = true;
    this.profileService.disableUserById(this.currentUserId).pipe(
      tap(deleted => {
        this.isLoading = false;
        if (deleted) {
          this.profileForm.reset();
          
          sessionStorage.removeItem('authToken');
          sessionStorage.removeItem('currentUserId');

          this.router.navigateByUrl('/login');
        }
      })
    ).subscribe();
  }

  onEdit(): void {
    this.isEditing = true;
  }

  onCompleteEdit(): void
  {
    this.isLoading = true;
    this.profileService.updateProfile(this.currentUserId, this.profileForm.value).pipe(
      tap(updated => {
        this.isLoading = false;
        if (updated) {
          this.profileForm.reset();
          this.isEditing = false;
          this.initObservables();
        }
      })
    ).subscribe()
  }
}