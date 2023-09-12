import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { TransferValue } from "../models/transfer.model";
import { catchError, map, of } from "rxjs";
import { environment } from "src/app/environments/environment";

@Injectable()
export class TransferService {
    
    constructor(private httpClient: HttpClient) { }

    payMyBuddy(transferValue: TransferValue) {
        return this.httpClient.post(`${environment.apiUrl}/transaction`, transferValue).pipe(
            map(() => true),
            catchError(() => of(false))
        );
    }
}