import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, catchError, map, of, tap } from "rxjs";
import { environment } from "src/app/environments/environment";
import { User } from "src/app/core/models/user.model";
import { TransferValue } from "../models/transfer.model";

@Injectable()
export class TransferService {
    
    constructor(private httpClient: HttpClient) { }

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

    payMyBuddy(transferValue: TransferValue) {
        return this.httpClient.post(`${environment.apiUrl}/transaction`, transferValue).pipe(
            tap((apiResponse) => {
                let updatedEwalletAmount = Number(sessionStorage.getItem('ewalletAmount')) - Number(JSON.parse(JSON.stringify(apiResponse)).amount*1.005);
                sessionStorage.setItem('ewalletAmount', JSON.stringify(updatedEwalletAmount));
            }),
            map(() => true),
            catchError(() => of(false))
        );
    }
}