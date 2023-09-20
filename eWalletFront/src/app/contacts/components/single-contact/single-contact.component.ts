import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Observable, switchMap } from 'rxjs';
import { ContactsService } from '../../services/contacts.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/core/models/user.model';
import { TransactionsService } from 'src/app/shared/services/transactions.service';
import { Sorter } from 'src/app/shared/models/sorter.model';

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
  transfersSorter$!: Observable<Sorter>;
  buddy$!: Observable<User>;

  ngOnInit(): void {
    this.initObservables();
  }

  private initObservables() {
    this.buddy$ = this.activatedRoute.params.pipe(
      switchMap(params => this.contactsService.getBuddyById(this.currentUserId, +params['id']))
    );
    this.transfersSorter$ = this.transactionsService.transfersSorter$;

    this.activatedRoute.params.subscribe(params => {
      this.transactionsService.getHistoryBetween(this.currentUserId, +params['id']);
    });
  }
  
  onOrderHistoryBetweenByDateAsc() {
    this.activatedRoute.params.subscribe(params => {
      this.transactionsService.getHistoryBetween_orderByDateAsc(this.currentUserId, +params['id']);
    });
  }
  onOrderHistoryBetweenByDateDesc() {
      this.activatedRoute.params.subscribe(params => {
        this.transactionsService.getHistoryBetween_orderByDateDesc(this.currentUserId, +params['id']);
      });
  }

  onOrderHistoryBetweenByAmountAsc() {
    this.activatedRoute.params.subscribe(params => {
      this.transactionsService.getHistoryBetween_orderByAmountAsc(this.currentUserId, +params['id']);
    });
  }
  onOrderHistoryBetweenByAmountDesc() {
    this.activatedRoute.params.subscribe(params => {
      this.transactionsService.getHistoryBetween_orderByAmountDesc(this.currentUserId, +params['id']);
    });
  }

  onBack() {
    this.router.navigateByUrl('/contacts');
  }
}
