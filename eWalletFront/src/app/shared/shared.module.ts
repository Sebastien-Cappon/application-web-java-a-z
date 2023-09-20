import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './material.module';
import { TransactionsService } from './services/transactions.service';
import { TransactionListComponent } from './components/transaction-list/transaction-list.component';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { AdminOverviewComponent } from './components/admin-overview/admin-overview.component';
import { AdminService } from './services/admin.service';
import { UsersService } from './services/users.service';

@NgModule({
  declarations: [
    TransactionListComponent,
    AdminOverviewComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    MaterialModule,
    CurrencyMaskModule,
    TransactionListComponent
  ],
  providers: [
    AdminService,
    TransactionsService,
    UsersService
  ]
})
export class SharedModule { }
