import { ChangeDetectionStrategy, Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, combineLatest, map, startWith, tap } from 'rxjs';
import { User } from 'src/app/core/models/user.model';
import { TransferService } from '../../services/transfer.service';
import { TransactionsService } from 'src/app/shared/services/transactions.service';
import { UsersService } from 'src/app/shared/services/users.service';

@Component({
  selector: 'app-new-transfer',
  templateUrl: './new-transfer.component.html',
  styleUrls: ['./new-transfer.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewTransferComponent {
  
  constructor(
    private formBuilder: FormBuilder,
    private transferService: TransferService,
    private transactionsService: TransactionsService,
    private usersService: UsersService
  ) { }

  transferForm!: FormGroup;
  transferAmountCtrl!: FormControl;
  transferAmount2Ctrl!: FormControl;
  transferBeneficiaryCtrl!: FormControl;
  transferCommentCtrl!: FormControl;

  isLoading = false;

  private currentUserId = Number(sessionStorage.getItem('currentUserId'));
  private maxAmount!: number;
  currentUser$!: Observable<User>;
  activeBuddies$!: Observable<User[]>;
  totalAmount$!: Observable<number>;

  ngOnInit(): void {
    this.initObservables();
    this.initTransferFormControls();
    this.initTransferForm();
    this.initTransferFormObservables();
  }

  initObservables() {
    this.currentUser$ = this.usersService.getUserWalletById(this.currentUserId);
    this.transferService.getMyActiveBuddies(this.currentUserId);
    
    // Unnecessary sessionStorage update, installed as a security measure.
    this.currentUser$.pipe(
      tap((currentUser) => {
        sessionStorage.setItem('ewalletAmount', JSON.stringify(currentUser.amount));
      })
    ).subscribe();

    this.maxAmount = Math.round((Number(sessionStorage.getItem('ewalletAmount'))/1.005)*100)/100;
  }

  initTransferFormControls() {
    this.transferBeneficiaryCtrl = this.formBuilder.control('', Validators.required);
    this.transferAmountCtrl = this.formBuilder.control(2, [Validators.min(2), Validators.max(this.maxAmount)]);
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
    const searchBeneficiary$ = this.transferBeneficiaryCtrl.valueChanges.pipe(
      startWith(this.transferBeneficiaryCtrl.value),
      map(value => {
        const test = typeof value === 'string' ? value.toLowerCase() : value.firstname.concat(" ").concat(value.lastname).toLowerCase();
        return test;
      })
    );

    this.activeBuddies$ = combineLatest([
      searchBeneficiary$,
      this.transferService.activeBuddies$
    ]).pipe(
      map(([searchBeneficiary, activeBuddies]) => activeBuddies.filter(activeBuddy => activeBuddy.firstname.concat(" ").concat(activeBuddy.lastname)
        .toLowerCase()
        .includes(searchBeneficiary)))
    );

    this.totalAmount$ = this.transferAmountCtrl.valueChanges.pipe(
      startWith(this.transferAmountCtrl.value),
      map(value => Math.round(value*1.005*100)/100)
    );
  }

  displayFn(selectedBuddy: User) {
    return selectedBuddy ? selectedBuddy.firstname + " " + selectedBuddy.lastname : '';
  }

  getTransferAmountControlErrorText(ctrl: AbstractControl): string {
    if(ctrl.hasError('min')) {
      return 'No transfer allowed under 2€.';
    } else if(ctrl.hasError('max')) {
      return 'No more than ' + this.maxAmount + '€';
    } else {
      return 'An error has occured.';
    }
  }

  onPayMyBuddy() {
    this.isLoading = true;
    this.transferService.payMyBuddy(this.transferForm.value).pipe(
      tap(transfered => {
        if (transfered) {
          this.isLoading = false;
          this.transactionsService.getHistory(this.currentUserId);
          this.ngOnInit();
        }
      })
    ).subscribe();
  }
}