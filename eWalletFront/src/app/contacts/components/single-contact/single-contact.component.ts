import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Observable, switchMap } from 'rxjs';
import { ContactsService } from '../services/contacts.service';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/core/models/user.model';

@Component({
  selector: 'app-single-contact',
  templateUrl: './single-contact.component.html',
  styleUrls: ['./single-contact.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SingleContactComponent {

  constructor(
    private contactsService: ContactsService,
    private activatedRoute: ActivatedRoute
  ) { }

  buddy$!: Observable<User>;

  ngOnInit(): void {
    this.initObservables();
  }

  private initObservables() {
    this.buddy$ = this.activatedRoute.params.pipe(
      switchMap(params => this.contactsService.getBuddyById(+params['id']))
    );
  }
  
}
