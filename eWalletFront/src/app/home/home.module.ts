import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeRoutingModule } from './home-routing.module';
import { SharedModule } from '../shared/shared.module';
import { HomeComponent } from './components/home/home.component';
import { HomeEwalletComponent } from './components/home-ewallet/home-ewallet.component';
import { HomeAdminComponent } from './components/home-admin/home-admin.component';
import { HomeEwalletHistoryComponent } from './components/home-ewallet-history/home-ewallet-history.component';
import { HomeBuddyListComponent } from './components/home-buddy-list/home-buddy-list.component';
import { ReactiveFormsModule } from '@angular/forms';
import { EwalletService } from './services/ewallet.service';


@NgModule({
  declarations: [
    HomeComponent,
    HomeEwalletComponent,
    HomeAdminComponent,
    HomeEwalletHistoryComponent,
    HomeBuddyListComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    SharedModule,
    ReactiveFormsModule
  ],
  exports: [
    HomeComponent
  ],
  providers: [
    EwalletService
  ]
})
export class HomeModule { }
