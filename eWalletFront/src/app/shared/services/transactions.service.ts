import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, tap } from "rxjs";
import { Transaction } from "src/app/core/models/transaction.model";
import { environment } from "src/app/environments/environment";

@Injectable()
export class TransactionsService {

    constructor(private httpClient: HttpClient) { }

    private _transactions$ = new BehaviorSubject<Transaction[]>([]);
    get transactions$(): Observable<Transaction[]> {
        return this._transactions$.asObservable();
    }

    getTransactions() {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/transactions`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistory(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistory_orderByDateDesc(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}?sortBy=date&order=desc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistory_orderByDateAsc(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}?sortBy=date&order=asc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistory_orderByBuddyDesc(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}?sortBy=buddy&order=desc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistory_orderByBuddyAsc(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}?sortBy=buddy&order=asc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistory_orderByAmountDesc(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}?sortBy=amount&order=desc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistory_orderByAmountAsc(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}?sortBy=amount&order=asc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistoryBetween(userId: number, buddyId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/between/${userId}-${buddyId}`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistoryBetween_orderByDateDesc(userId: number, buddyId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/between/${userId}-${buddyId}?sortBy=date&order=desc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistoryBetween_orderByDateAsc(userId: number, buddyId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/between/${userId}-${buddyId}?sortBy=date&order=asc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistoryBetween_orderByAmountDesc(userId: number, buddyId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/between/${userId}-${buddyId}?sortBy=amount&order=desc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistoryBetween_orderByAmountAsc(userId: number, buddyId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/between/${userId}-${buddyId}?sortBy=amount&order=asc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }
}