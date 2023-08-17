import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
  { path: 'auth/login', loadChildren: () => import('./auth/auth-routing.module').then(m => m.AuthRoutingModule) },
  { path: 'home', loadChildren: () => import('./home/home-routing.module').then(m => m.HomeRoutingModule), canActivate: [AuthGuard] },
  { path: 'profile', loadChildren: () => import('./profile/profile-routing.module').then(m => m.ProfileRoutingModule), canActivate: [AuthGuard] },
  { path: 'transfer', loadChildren: () => import('./transfer/transfer-routing.module').then(m => m.TransferRoutingModule), canActivate: [AuthGuard] },
  { path: 'contacts', loadChildren: () => import('./contacts/contacts-routing.module').then(m => m.ContactsRoutingModule), canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
