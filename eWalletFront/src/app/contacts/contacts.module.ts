import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContactsRoutingModule } from './contacts-routing.module';
import { ContactListComponent } from './components/contact-list/contact-list.component';
import { SingleContactComponent } from './components/single-contact/single-contact.component';
import { SharedModule } from '../shared/shared.module';
import { ContactsService } from './components/services/contacts.service';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    ContactListComponent,
    SingleContactComponent
  ],
  imports: [
    CommonModule,
    ContactsRoutingModule,
    SharedModule,
    ReactiveFormsModule
  ],
  providers: [
    ContactsService
  ]
})
export class ContactsModule { }
