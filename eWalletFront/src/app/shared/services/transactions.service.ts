import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, catchError, map, of, tap } from "rxjs";
import { Transaction } from "src/app/core/models/transaction.model";
import { environment } from "src/app/environments/environment";
import { Sorter } from "../models/sorter.model";

@Injectable()
export class TransactionsService {

    constructor(private httpClient: HttpClient) { }
    
    private _transfersSorter$ = new BehaviorSubject<Sorter>({
        sortBy: "",
        orderBy: ""
    })
    get transfersSorter$(): Observable<Sorter> {
        return this._transfersSorter$.asObservable();
    }

    private _transactions$ = new BehaviorSubject<Transaction[]>([]);
    get transactions$(): Observable<Transaction[]> {
        return this._transactions$.asObservable();
    }

    private setTransferSorterStatus(sorter: Sorter) {
        this._transfersSorter$.next(sorter);
    }

    getHistory(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
                this.setTransferSorterStatus({sortBy: "", orderBy: ""});
            })
        ).subscribe();
    }

    getHistory_orderByDateDesc(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}?sortBy=date&order=desc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
                this.setTransferSorterStatus({sortBy: "date", orderBy: "desc"});
            })
        ).subscribe();
    }

    getHistory_orderByDateAsc(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}?sortBy=date&order=asc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
                this.setTransferSorterStatus({sortBy: "date", orderBy: "asc"});
            })
        ).subscribe();
    }

    getHistory_orderByBuddyDesc(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}?sortBy=buddy&order=desc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
                this.setTransferSorterStatus({sortBy: "buddy", orderBy: "desc"});
            })
        ).subscribe();
    }

    getHistory_orderByBuddyAsc(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}?sortBy=buddy&order=asc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
                this.setTransferSorterStatus({sortBy: "buddy", orderBy: "asc"});
            })
        ).subscribe();
    }

    getHistory_orderByAmountDesc(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}?sortBy=amount&order=desc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
                this.setTransferSorterStatus({sortBy: "amount", orderBy: "desc"});
            })
        ).subscribe();
    }

    getHistory_orderByAmountAsc(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/${userId}?sortBy=amount&order=asc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
                this.setTransferSorterStatus({sortBy: "amount", orderBy: "asc"});
            })
        ).subscribe();
    }

    getEwalletHistory(userId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/ewallet/${userId}`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
            })
        ).subscribe();
    }

    getHistoryBetween(userId: number, buddyId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/between/${userId}-${buddyId}`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
                this.setTransferSorterStatus({sortBy: "", orderBy: ""});
            })
        ).subscribe();
    }

    getHistoryBetween_orderByDateDesc(userId: number, buddyId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/between/${userId}-${buddyId}?sortBy=date&order=desc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
                this.setTransferSorterStatus({sortBy: "date", orderBy: "desc"});
            })
        ).subscribe();
    }

    getHistoryBetween_orderByDateAsc(userId: number, buddyId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/between/${userId}-${buddyId}?sortBy=date&order=asc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
                this.setTransferSorterStatus({sortBy: "date", orderBy: "asc"});
            })
        ).subscribe();
    }

    getHistoryBetween_orderByAmountDesc(userId: number, buddyId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/between/${userId}-${buddyId}?sortBy=amount&order=desc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
                this.setTransferSorterStatus({sortBy: "amount", orderBy: "desc"});
            })
        ).subscribe();
    }

    getHistoryBetween_orderByAmountAsc(userId: number, buddyId: number) {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/history/between/${userId}-${buddyId}?sortBy=amount&order=asc`).pipe(
            tap(transactions => {
                this._transactions$.next(transactions);
                this.setTransferSorterStatus({sortBy: "amount", orderBy: "asc"});
            })
        ).subscribe();
    }
}