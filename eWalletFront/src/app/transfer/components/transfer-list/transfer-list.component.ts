import { Component } from '@angular/core';
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
  isOrderedByDateAsc = false;
  isOrderedByBuddyAsc = false;
  isOrderedByAmountAsc = false;

  ngOnInit() {
    this.initObservables();
  }

  initObservables() {
    this.transactionsService.getHistory(this.currentUserId);
  }

  onOrderHistoryByDate() {
    if(!this.isOrderedByDateAsc){
      this.transactionsService.getHistory_orderByDateAsc(this.currentUserId);
      this.isOrderedByDateAsc = true;
    } else {
      this.transactionsService.getHistory_orderByDateDesc(this.currentUserId);
      this.isOrderedByDateAsc = false;
    }
  }

  onOrderHistoryByBuddy() {
    if(!this.isOrderedByBuddyAsc){
      this.transactionsService.getHistory_orderByBuddyAsc(this.currentUserId);
      this.isOrderedByBuddyAsc = true;
    } else {
      this.transactionsService.getHistory_orderByBuddyDesc(this.currentUserId);
      this.isOrderedByBuddyAsc = false;
    }
  }

  onOrderHistoryByAmount() {
    if(!this.isOrderedByAmountAsc){
      this.transactionsService.getHistory_orderByAmountAsc(this.currentUserId);
      this.isOrderedByAmountAsc = true;
    } else {
      this.transactionsService.getHistory_orderByAmountDesc(this.currentUserId);
      this.isOrderedByAmountAsc = false;
    }
  }
}
