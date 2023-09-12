import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { BehaviorSubject, Observable, Subscription, map, startWith, tap } from 'rxjs';
import { ContactsService } from 'src/app/contacts/components/services/contacts.service';
import { User } from 'src/app/core/models/user.model';
import { TransferService } from '../../services/transfer.service';
import { TransactionsService } from 'src/app/shared/services/transactions.service';
import { Transaction } from 'src/app/core/models/transaction.model';

@Component({
  selector: 'app-new-transfer',
  templateUrl: './new-transfer.component.html',
  styleUrls: ['./new-transfer.component.scss']
})
export class NewTransferComponent {
  
  constructor(
    private formBuilder: FormBuilder,
    private contactsService: ContactsService,
    private transferService: TransferService,
    private transactionsService: TransactionsService
  ) { }

  transferForm!: FormGroup;
  transferAmountCtrl!: FormControl;
  transferBeneficiaryCtrl!: FormControl;
  transferCommentCtrl!: FormControl;

  isLoading = false;

  private currentUserId = Number(sessionStorage.getItem('currentUserId'));
  buddies$!: Observable<User[]>;
  
  buddies!: User[];
  filteredBuddies$!: Observable<User[]>;

  ngOnInit(): void {
    this.initObservables();
    this.initTransferFormControls();
    this.initTransferForm();
    this.initTransferFormObservables();
  }

  initObservables() {
    this.contactsService.getMyBuddies(this.currentUserId);
    this.buddies$ = this.contactsService.buddies$;
  }

  initTransferFormControls() {
    this.transferBeneficiaryCtrl = this.formBuilder.control('', Validators.required);
    this.transferAmountCtrl = this.formBuilder.control(2, Validators.min(2));
    this.transferCommentCtrl = this.formBuilder.control('');
  }

  initTransferForm() {
    this.transferForm = this.formBuilder.group({
      senderId: this.currentUserId,
      receiver: this.transferBeneficiaryCtrl,
      amount: this.transferAmountCtrl,
      comment: this.transferCommentCtrl
    })
  }

  initTransferFormObservables() {
    this.buddies$.subscribe(buddies => this.buddies = buddies);
    this.filteredBuddies$ = this.transferBeneficiaryCtrl.valueChanges.pipe(
      startWith(''),
      map(buddy => (buddy ? this._filterBuddies(buddy) : this.buddies.slice())),
    );
  }

  private _filterBuddies(value: string): User[] {
    const filterValue = value.toLowerCase();
    this.buddies = this.buddies.filter(buddy => buddy.active);
    return this.buddies.filter(buddy => buddy.firstname.toLowerCase().concat(buddy.lastname.toLowerCase()).includes(filterValue));
  }

  displayFn(selectedBuddy: User) {
    return selectedBuddy ? selectedBuddy.firstname + " " + selectedBuddy.lastname : '';
  }

  onPayMyBuddy() {
    this.isLoading = true;
    this.transferService.payMyBuddy(this.transferForm.value).pipe(
      tap(transfered => {
        if (transfered) {
          this.isLoading = false;
          this.transferForm.reset();
          this.transactionsService.getHistory(this.currentUserId);
          this.ngOnInit();
        }
      })
    ).subscribe();
  }
}
