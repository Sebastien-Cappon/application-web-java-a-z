import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Observable, switchMap } from 'rxjs';
import { ContactsService } from '../services/contacts.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/core/models/user.model';
import { TransactionsService } from 'src/app/shared/services/transactions.service';

@Component({
  selector: 'app-single-contact',
  templateUrl: './single-contact.component.html',
  styleUrls: ['./single-contact.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SingleContactComponent {

  constructor(
    private contactsService: ContactsService,
    private transactionsService: TransactionsService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) { }

  private currentUserId = Number(sessionStorage.getItem('currentUserId'));
  buddy$!: Observable<User>;

  isOrderedByDateAsc = false;
  isOrderedByBuddyAsc = false;
  isOrderedByAmountAsc = false;

  ngOnInit(): void {
    this.initObservables();
  }

  private initObservables() {
    this.buddy$ = this.activatedRoute.params.pipe(
      switchMap(params => this.contactsService.getBuddyById(this.currentUserId, +params['id']))
    );

    this.activatedRoute.params.subscribe(params => {
      this.transactionsService.getHistoryBetween(this.currentUserId, +params['id']);
    });
  }
  
  onOrderHistoryBetweenByDate() {
    if(!this.isOrderedByDateAsc){
      this.activatedRoute.params.subscribe(params => {
        this.transactionsService.getHistoryBetween_orderByDateAsc(this.currentUserId, +params['id']);
      });
      this.isOrderedByDateAsc = true;
    } else {
      this.activatedRoute.params.subscribe(params => {
        this.transactionsService.getHistoryBetween_orderByDateDesc(this.currentUserId, +params['id']);
      });
      this.isOrderedByDateAsc = false;
    }
  }

  onOrderHistoryBetweenByAmount() {
    if(!this.isOrderedByAmountAsc){
      this.activatedRoute.params.subscribe(params => {
        this.transactionsService.getHistoryBetween_orderByAmountAsc(this.currentUserId, +params['id']);
      });
      this.isOrderedByAmountAsc = true;
    } else {
      this.activatedRoute.params.subscribe(params => {
        this.transactionsService.getHistoryBetween_orderByAmountDesc(this.currentUserId, +params['id']);
      });
      this.isOrderedByAmountAsc = false;
    }
  }

  onBack() {
    this.router.navigateByUrl('/contacts');
  }
}
