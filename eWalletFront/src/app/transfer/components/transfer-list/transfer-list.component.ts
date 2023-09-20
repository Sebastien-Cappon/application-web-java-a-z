import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Sorter } from 'src/app/shared/models/sorter.model';
import { TransactionsService } from 'src/app/shared/services/transactions.service';

@Component({
  selector: 'app-transfer-list',
  templateUrl: './transfer-list.component.html',
  styleUrls: ['./transfer-list.component.scss']
})
export class TransferListComponent {

  constructor(
    private transactionsService: TransactionsService
  ) { }

  private currentUserId = Number(sessionStorage.getItem('currentUserId'));
  transfersSorter$!: Observable<Sorter>;

  ngOnInit() {
    this.initObservables();
  }

  initObservables() {
    this.transfersSorter$ = this.transactionsService.transfersSorter$;
    this.transactionsService.getHistory(this.currentUserId);
  }

  onOrderHistoryByDateAsc() {
    this.transactionsService.getHistory_orderByDateAsc(this.currentUserId);
  }
  onOrderHistoryByDateDesc() {
    this.transactionsService.getHistory_orderByDateDesc(this.currentUserId);
  }

  onOrderHistoryByBuddyAsc() {
    this.transactionsService.getHistory_orderByBuddyAsc(this.currentUserId);
  }
  onOrderHistoryByBuddyDesc() {
    this.transactionsService.getHistory_orderByBuddyDesc(this.currentUserId);
  }

  onOrderHistoryByAmountAsc() {
    this.transactionsService.getHistory_orderByAmountAsc(this.currentUserId);
  }
  onOrderHistoryByAmountDesc() {
    this.transactionsService.getHistory_orderByAmountDesc(this.currentUserId);
  }
}
