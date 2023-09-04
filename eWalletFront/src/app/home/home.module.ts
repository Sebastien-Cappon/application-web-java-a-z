import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeRoutingModule } from './home-routing.module';
import { UserListComponent } from './components/user-list/user-list.component';
import { SharedModule } from '../shared/shared.module';
import { UserListItemComponent } from './components/user-list-item/user-list-item.component';


@NgModule({
  declarations: [
    UserListComponent,
    UserListItemComponent
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    SharedModule
  ],
  exports: [
    UserListComponent
  ]
})
export class HomeModule { }
