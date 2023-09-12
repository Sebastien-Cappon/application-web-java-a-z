import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './material.module';
import { UsersService } from './services/users.service';
import { TransactionsService } from './services/transactions.service';
import { TransactionListComponent } from './components/transaction-list/transaction-list.component';
import { CurrencyMaskModule } from 'ng2-currency-mask';

@NgModule({
  declarations: [
    TransactionListComponent
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
    UsersService,
    TransactionsService
  ]
})
export class SharedModule { }
