import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, Observable, tap } from "rxjs";
import { User } from "../../core/models/user.model";
import { environment } from "src/app/environments/environment";

@Injectable()
export class UsersService {

    constructor(private httpClient: HttpClient) { }
    
    private _users$ = new BehaviorSubject<User[]>([]);
    get users$(): Observable<User[]> {
        return this._users$.asObservable();
    }

    private lastRefresh = 0;

    getUsers() {
        if(Date.now() - this.lastRefresh <= 5*60*1000) {
            return;
        }

        this.httpClient.get<User[]>(`${environment.apiUrl}/users`).pipe(
            tap(users => {
                this.lastRefresh = Date.now();
                this._users$.next(users);
            })
        ).subscribe();
    }

}