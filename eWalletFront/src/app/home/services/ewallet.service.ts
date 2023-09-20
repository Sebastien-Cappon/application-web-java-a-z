import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/app/environments/environment";
import { BehaviorSubject, Observable, catchError, map, of, tap } from "rxjs";
import { User } from "src/app/core/models/user.model";
import { EwalletTransferValue } from "../models/ewallet-transfer.model";
import { Transaction } from "src/app/core/models/transaction.model";

@Injectable()
export class EwalletService {
    
    constructor(private httpClient: HttpClient) { }

    private _transactions$ = new BehaviorSubject<Transaction[]>([]);
    get transactions$(): Observable<Transaction[]> {
        return this._transactions$.asObservable();
    }

    homeEwalletTransfer(ewalletTransferValue: EwalletTransferValue): Observable<boolean> {
        return this.httpClient.post(`${environment.apiUrl}/ewallet`, ewalletTransferValue).pipe(
            tap((apiResponse) => {
                let updatedEwalletAmount = Number(sessionStorage.getItem('ewalletAmount')) + Number(JSON.parse(JSON.stringify(apiResponse)).amount);
                sessionStorage.setItem('ewalletAmount', JSON.stringify(updatedEwalletAmount));
            }),
            map(() => true),
            catchError(() => of(false))
        );
    }
}
