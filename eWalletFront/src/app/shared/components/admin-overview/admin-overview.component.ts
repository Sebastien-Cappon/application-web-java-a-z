import { Component } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Transaction } from 'src/app/core/models/transaction.model';
import { AdminService } from '../../services/admin.service';
import { Sorter } from '../../models/sorter.model';

@Component({
  selector: 'app-admin-overview',
  templateUrl: './admin-overview.component.html',
  styleUrls: ['./admin-overview.component.scss']
})
export class AdminOverviewComponent {

  constructor(
    private adminService: AdminService
  ) { }

  adminViewTransactions$!: Observable<Transaction[]>;
  adminViewTransfersSorter$!: Observable<Sorter>;
  
  transactionFeeTotal!: number;

  ngOnInit() {
    this.initObservables();
  }

  initObservables() {
    this.adminViewTransactions$ = this.adminService.adminViewTransactions$.pipe(
      tap(transactions => {
        this.transactionFeeTotal = 0;
        for(let transaction of transactions) {
          this.transactionFeeTotal += transaction.fee;
        }
      })
    );
    this.adminViewTransfersSorter$ = this.adminService.adminViewTransfersSorter$;
    this.adminService.getTransactions();
  }

  onOrderTransactionsByDateAsc() {
    this.adminService.getTransactions_orderByDateAsc();
  }
  onOrderTransactionsByDateDesc() {
    this.adminService.getTransactions_orderByDateDesc();
  }

  onOrderTransactionsBySenderAsc() {
    this.adminService.getTransactions_orderBySenderAsc();
  }
  onOrderTransactionsBySenderDesc() {
    this.adminService.getTransactions_orderBySenderDesc();
  }

  onOrderTransactionsByReceiverAsc() {
    this.adminService.getTransactions_orderByReceiverAsc();
  }
  onOrderTransactionsByReceiverDesc() {
    this.adminService.getTransactions_orderByReceiverDesc();
  }

  onOrderTransactionsByAmountAsc() {
    this.adminService.getTransactions_orderByAmountAsc();
  }
  onOrderTransactionsByAmountDesc() {
    this.adminService.getTransactions_orderByAmountDesc();
  }
}
