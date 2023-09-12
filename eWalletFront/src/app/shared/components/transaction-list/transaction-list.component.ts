import { Component } from '@angular/core';
import { TransactionsService } from '../../services/transactions.service';
import { Observable } from 'rxjs';
import { Transaction } from 'src/app/core/models/transaction.model';

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.scss']
})
export class TransactionListComponent {

  constructor(private transactionService: TransactionsService) { }

  currentUserId = Number(sessionStorage.getItem('currentUserId'));
  transactions$!: Observable<Transaction[]>;

  ngOnInit(): void {
    this.initObservables();
  }

  initObservables() {
    this.transactions$ = this.transactionService.transactions$;
  }
}
