import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/core/models/user.model';
import { UsersService } from 'src/app/shared/services/users.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserListComponent {

  constructor(
    private usersService: UsersService
  ) { }

  users$!: Observable<User[]>;

  ngOnInit(): void {
    this.initObservables();
    this.usersService.getUsers();
  }

  private initObservables() {
    this.users$ = this.usersService.users$;
  }
}