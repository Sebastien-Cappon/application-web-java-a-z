import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { TransferValue } from "../models/transfer.model";
import { BehaviorSubject, Observable, catchError, map, of, tap } from "rxjs";
import { environment } from "src/app/environments/environment";
import { User } from "src/app/core/models/user.model";

@Injectable()
export class TransferService {
    
    constructor(private httpClient: HttpClient) { }

    payMyBuddy(transferValue: TransferValue) {
        return this.httpClient.post(`${environment.apiUrl}/transaction`, transferValue).pipe(
            map(() => true),
            catchError(() => of(false))
        );
    }

    private _activeBuddies$ = new BehaviorSubject<User[]>([]);
    get activeBuddies$(): Observable<User[]> {
        return this._activeBuddies$.asObservable();
    }

    getMyActiveBuddies(userId: number) {
        this.httpClient.get<User[]>(`${environment.apiUrl}/mybuddies/active/${userId}`).pipe(
            tap(activeBuddies => {
                this._activeBuddies$.next(activeBuddies);
            })
        ).subscribe();
    }
}