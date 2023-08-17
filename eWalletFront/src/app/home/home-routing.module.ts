import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserListComponent } from './components/user-list/user-list.component';
import { UsersResolver } from '../core/resolvers/users.resolver';

const routes: Routes = [
  { path: '', component: UserListComponent, resolve: { users: UsersResolver } }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class HomeRoutingModule { }
