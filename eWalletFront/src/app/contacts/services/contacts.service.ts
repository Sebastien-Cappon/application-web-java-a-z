import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, catchError, map, of, tap } from "rxjs";
import { User } from "src/app/core/models/user.model";
import { environment } from "src/app/environments/environment";
import { ContactsValue } from "../models/contacts.model";

@Injectable()
export class ContactsService {

    constructor(private httpClient: HttpClient) { }

    private isBuddiesLoaded = false;

    private _buddies$ = new BehaviorSubject<User[]>([]);
    get buddies$(): Observable<User[]> {
        return this._buddies$.asObservable();
    }

    getMyBuddies(userId: number) {
        this.httpClient.get<User[]>(`${environment.apiUrl}/mybuddies/${userId}`).pipe(
            tap(buddies => {
                this.isBuddiesLoaded = true;
                this._buddies$.next(buddies);
            })
        ).subscribe();
    }

    getBuddyById(userId: number, id: number): Observable<User> {
        if(!this.isBuddiesLoaded) {
            this.getMyBuddies(userId);
            this.isBuddiesLoaded = true;
        }

        return this.buddies$.pipe(
            map(buddies => buddies.filter(buddy => buddy.id === id)[0])
        );
    }

    addBuddy(contactsValue: ContactsValue): Observable<boolean> {
        return this.httpClient.post(`${environment.apiUrl}/buddy`, contactsValue).pipe(
            map(() => true),
            catchError(() => of(false))
        );
    }

    unBuddy(userId: number, buddyId: number) {
        return this.httpClient.delete(`${environment.apiUrl}/buddy/${userId}-${buddyId}`);
    }
}