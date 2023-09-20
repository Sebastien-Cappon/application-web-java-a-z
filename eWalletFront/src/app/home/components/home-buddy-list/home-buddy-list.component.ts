import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { ContactsService } from 'src/app/contacts/services/contacts.service';
import { User } from 'src/app/core/models/user.model';

@Component({
  selector: 'app-home-buddy-list',
  templateUrl: './home-buddy-list.component.html',
  styleUrls: ['./home-buddy-list.component.scss']
})
export class HomeBuddyListComponent {

  constructor(
    private contactsService: ContactsService
  ) { }

  private currentUserId = Number(sessionStorage.getItem('currentUserId'));
  buddies$!: Observable<User[]>;

  ngOnInit() {
    this.initObservables();
  }

  initObservables() {
    this.contactsService.getMyBuddies(this.currentUserId);
    this.buddies$ = this.contactsService.buddies$;
  }
}
