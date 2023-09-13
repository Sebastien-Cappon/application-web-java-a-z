import { Component } from '@angular/core';
import { ContactsService } from '../../services/contacts.service';
import { Observable, tap } from 'rxjs';
import { User } from 'src/app/core/models/user.model';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { emailPatternValidator } from 'src/app/shared/validators/emailPattern.validator';

@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.scss']
})
export class ContactListComponent {

  constructor(
    private contactsService: ContactsService,
    private formBuilder: FormBuilder
  ) { }
  
  private currentUserId = Number(sessionStorage.getItem('currentUserId'));
  loading$!: Observable<boolean>;
  buddies$!: Observable<User[]>;
  users$!: Observable<User[]>;

  newBuddyForm!: FormGroup;
  newBuddyEmailCtrl!: FormControl;

  isLoading = false;

  ngOnInit(): void {
    this.initNewBuddyFormControls();
    this.initNewBuddyForm();
    this.initObservables();
  }

  private initNewBuddyFormControls() {
    this.newBuddyEmailCtrl = this.formBuilder.control('', [Validators.required, Validators.email, emailPatternValidator()]);
  }

  private initNewBuddyForm() {
    this.newBuddyForm = this.formBuilder.group({
      userId: this.currentUserId,
      newBuddyEmail: this.newBuddyEmailCtrl
    });
  }

  private initObservables() {
    this.contactsService.getMyBuddies(this.currentUserId);
    this.buddies$ = this.contactsService.buddies$;
  }

  getNewBuddyEmailControlErrorText(ctrl: AbstractControl): string {
    if(ctrl.hasError('required')) {
      return 'Please enter your future buddy email address.';
    } else if (ctrl.hasError('email') || ctrl.hasError('emailPatternValidator')){
      return 'This input filed require a valid email address.'
    } else {
      return 'An error has occured.';
    }
  }

  onUnbuddy(buddyId: number) {
    this.isLoading = true;
    this.contactsService.unBuddy(this.currentUserId, buddyId).subscribe(() => {
      this.isLoading = false;
      this.initObservables();
    });
  }

  onAddBuddy() {
    this.contactsService.addBuddy(this.newBuddyForm.value).pipe(
      tap(buddied => {
        if (buddied) {
          this.newBuddyEmailCtrl.reset();
          this.initObservables();
        }
      })
    ).subscribe();
  }
}
