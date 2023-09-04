import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContactListComponent } from './components/contact-list/contact-list.component';
import { SingleContactComponent } from './components/single-contact/single-contact.component';

const routes: Routes = [
  { path: '', component: ContactListComponent },
  { path: ':id', component: SingleContactComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class ContactsRoutingModule { }
