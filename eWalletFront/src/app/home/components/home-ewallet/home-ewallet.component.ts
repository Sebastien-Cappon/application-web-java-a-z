import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TransactionsService } from 'src/app/shared/services/transactions.service';
import { EwalletService } from '../../services/ewallet.service';
import { Observable, tap } from 'rxjs';
import { User } from 'src/app/core/models/user.model';
import { UsersService } from 'src/app/shared/services/users.service';

@Component({
  selector: 'app-home-ewallet',
  templateUrl: './home-ewallet.component.html',
  styleUrls: ['./home-ewallet.component.scss']
})
export class HomeEwalletComponent {

  constructor(
    private formBuilder: FormBuilder,
    private ewalletService: EwalletService,
    private usersService: UsersService,
    private transactionsService: TransactionsService
  ) { }

  homeEwalletForm!: FormGroup;
  homeCreditAmountCtrl!: FormControl;

  private currentUserId = Number(sessionStorage.getItem('currentUserId'));
  currentUser$!: Observable<User>;
  currentUser!: User;
  maxAmount = Number(sessionStorage.getItem('ewalletAmount'));

  isDebitLoading = false;
  isCreditLoading = false;
  

  ngOnInit() {
    this.initObservables();
    this.initHomeFormControls();
    this.initHomeForm();
  }

  initObservables() {
    this.currentUser$ = this.usersService.getUserWalletById(this.currentUserId).pipe(
      tap(user => {
        this.currentUser = user;
      })
    );
  }

  initHomeFormControls() {
    this.homeCreditAmountCtrl = this.formBuilder.control(null, Validators.min(3));
  }

  initHomeForm() {
    this.homeEwalletForm = this.formBuilder.group({
      userId: this.currentUserId,
      amount: this.homeCreditAmountCtrl,
    });
  }

  onDebitEwallet() {
    let ewalletDebitValue = {
      userId: this.currentUserId,
      amount: -this.currentUser.amount
    }

    this.isDebitLoading = true;
    this.ewalletService.homeEwalletTransfer(ewalletDebitValue).pipe(
      tap(credited => {
        if(credited) {
          this.isDebitLoading = false;
          this.homeEwalletForm.reset();
          this.ngOnInit();
          this.transactionsService.getEwalletHistory(this.currentUserId);
        }
      })
    ).subscribe();
  }

  onCreditEwallet() {
    this.isCreditLoading = true;
    this.ewalletService.homeEwalletTransfer(this.homeEwalletForm.value).pipe(
      tap(credited => {
        if(credited) {
          this.isCreditLoading = false;
          this.homeEwalletForm.reset();
          this.ngOnInit();
          this.transactionsService.getEwalletHistory(this.currentUserId);
        }
      })
    ).subscribe();
  }
}