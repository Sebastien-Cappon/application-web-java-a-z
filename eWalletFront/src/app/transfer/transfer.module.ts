import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TransferRoutingModule } from './transfer-routing.module';
import { TransferListComponent } from './components/transfer-list/transfer-list.component';
import { SharedModule } from '../shared/shared.module';
import { TransferService } from './services/transfer.service';
import { NewTransferComponent } from './components/new-transfer/new-transfer.component';
import { TransferComponent } from './components/transfer/transfer.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    TransferListComponent,
    NewTransferComponent,
    TransferComponent
  ],
  imports: [
    CommonModule,
    TransferRoutingModule,
    SharedModule,
    ReactiveFormsModule
  ],
  providers: [
    TransferService
  ]
})
export class TransferModule { }
