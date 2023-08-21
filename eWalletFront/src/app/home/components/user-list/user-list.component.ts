import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map } from 'rxjs';
import { User } from 'src/app/core/models/user.model';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent {

  users$!: Observable<User[]>;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.users$ = this.route.data.pipe(
      map(data => data['users'])
    );
  }
}