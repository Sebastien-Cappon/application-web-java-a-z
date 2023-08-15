import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'home', loadChildren: () => import('./home/home-routing.module').then(m => m.HomeRoutingModule) },
  { path: 'profile', loadChildren: () => import('./profile/profile-routing.module').then(m => m.ProfileRoutingModule) },
  { path: 'transfer', loadChildren: () => import('./transfer/transfer-routing.module').then(m => m.TransferRoutingModule) },
  { path: 'contacts', loadChildren: () => import('./contacts/contacts-routing.module').then(m => m.ContactsRoutingModule) },
  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
