import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, combineLatest, map, startWith, tap } from 'rxjs';
import { User } from 'src/app/core/models/user.model';
import { TransferService } from '../../services/transfer.service';
import { TransactionsService } from 'src/app/shared/services/transactions.service';

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
    private transactionsService: TransactionsService
  ) { }

  transferForm!: FormGroup;
  transferAmountCtrl!: FormControl;
  transferBeneficiaryCtrl!: FormControl;
  transferCommentCtrl!: FormControl;

  isLoading = false;

  private currentUserId = Number(sessionStorage.getItem('currentUserId'));
  activeBuddies$!: Observable<User[]>

  ngOnInit(): void {
    this.initObservables();
    this.initTransferFormControls();
    this.initTransferForm();
    this.initTransferFormObservables();
  }

  initObservables() {
    this.transferService.getMyActiveBuddies(this.currentUserId);
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
    const searchBeneficiary$ = this.transferBeneficiaryCtrl.valueChanges.pipe(
      startWith(this.transferBeneficiaryCtrl.value),
      map(value => {
        const test = typeof value === 'string' ? value.toLowerCase() : value.firstname.concat(" ").concat(value.lastname).toLowerCase();
        return test;
      }),
      tap(value => console.log(value))
    );

    this.activeBuddies$ = combineLatest([
      searchBeneficiary$,
      this.transferService.activeBuddies$
    ]).pipe(
      map(([searchBeneficiary, activeBuddies]) => activeBuddies.filter(activeBuddy => activeBuddy.firstname.concat(" ").concat(activeBuddy.lastname)
        .toLowerCase()
        .includes(searchBeneficiary)))
    );
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