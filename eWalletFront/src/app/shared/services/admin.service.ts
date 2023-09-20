import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, tap } from "rxjs";
import { Transaction } from "src/app/core/models/transaction.model";
import { environment } from "src/app/environments/environment";
import { Sorter } from "../models/sorter.model";

@Injectable()
export class AdminService {

    constructor(private httpClient: HttpClient) { }

    private _adminViewTransfersSorter$ = new BehaviorSubject<Sorter>({
        sortBy: "",
        orderBy: ""
    })
    get adminViewTransfersSorter$(): Observable<Sorter> {
        return this._adminViewTransfersSorter$.asObservable();
    }

    private _adminViewTransactions$ = new BehaviorSubject<Transaction[]>([]);
    get adminViewTransactions$(): Observable<Transaction[]> {
        return this._adminViewTransactions$.asObservable();
    }

    private setAdminViewTransferSorterStatus(sorter: Sorter) {
        this._adminViewTransfersSorter$.next(sorter);
    }

    getTransactions() {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/transactions`).pipe(
            tap(transactions => {
                this._adminViewTransactions$.next(transactions);
                this.setAdminViewTransferSorterStatus({sortBy: "", orderBy: ""});
            })
        ).subscribe();
    }

    getTransactions_orderByDateDesc() {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/transactions?sortBy=date&order=desc`).pipe(
            tap(transactions => {
                this._adminViewTransactions$.next(transactions);
                this.setAdminViewTransferSorterStatus({sortBy: "date", orderBy: "desc"});
            })
        ).subscribe();
    }

    getTransactions_orderByDateAsc() {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/transactions?sortBy=date&order=asc`).pipe(
            tap(transactions => {
                this._adminViewTransactions$.next(transactions);
                this.setAdminViewTransferSorterStatus({sortBy: "date", orderBy: "asc"});
            })
        ).subscribe();
    }

    getTransactions_orderBySenderDesc() {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/transactions?sortBy=sender&order=desc`).pipe(
            tap(transactions => {
                this._adminViewTransactions$.next(transactions);
                this.setAdminViewTransferSorterStatus({sortBy: "sender", orderBy: "desc"});
            })
        ).subscribe();
    }

    getTransactions_orderBySenderAsc() {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/transactions?sortBy=sender&order=asc`).pipe(
            tap(transactions => {
                this._adminViewTransactions$.next(transactions);
                this.setAdminViewTransferSorterStatus({sortBy: "sender", orderBy: "asc"});
            })
        ).subscribe();
    }

    getTransactions_orderByReceiverDesc() {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/transactions?sortBy=receiver&order=desc`).pipe(
            tap(transactions => {
                this._adminViewTransactions$.next(transactions);
                this.setAdminViewTransferSorterStatus({sortBy: "receiver", orderBy: "desc"});
            })
        ).subscribe();
    }

    getTransactions_orderByReceiverAsc() {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/transactions?sortBy=receiver&order=asc`).pipe(
            tap(transactions => {
                this._adminViewTransactions$.next(transactions);
                this.setAdminViewTransferSorterStatus({sortBy: "receiver", orderBy: "asc"});
            })
        ).subscribe();
    }


    getTransactions_orderByAmountDesc() {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/transactions?sortBy=amount&order=desc`).pipe(
            tap(transactions => {
                this._adminViewTransactions$.next(transactions);
                this.setAdminViewTransferSorterStatus({sortBy: "amount", orderBy: "desc"});
            })
        ).subscribe();
    }


    getTransactions_orderByAmountAsc() {
        this.httpClient.get<Transaction[]>(`${environment.apiUrl}/transactions?sortBy=amount&order=asc`).pipe(
            tap(transactions => {
                this._adminViewTransactions$.next(transactions);
                this.setAdminViewTransferSorterStatus({sortBy: "amount", orderBy: "asc"});
            })
        ).subscribe();
    }
}