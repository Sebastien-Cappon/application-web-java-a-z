import { Component } from '@angular/core';
import { TransactionsService } from 'src/app/shared/services/transactions.service';

@Component({
  selector: 'app-home-ewallet-history',
  templateUrl: './home-ewallet-history.component.html',
  styleUrls: ['./home-ewallet-history.component.scss']
})
export class HomeEwalletHistoryComponent {

  constructor(
    private transactionsService: TransactionsService
  ) { }

  private currentUserId = Number(sessionStorage.getItem('currentUserId'));

  ngOnInit() {
    this.initObservables();
  }

  initObservables() {
    this.transactionsService.getEwalletHistory(this.currentUserId);
  }
}
