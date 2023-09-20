import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { NewUserValue } from "../models/new-user.model";
import { Observable, catchError, map, of, tap } from "rxjs";
import { environment } from "src/app/environments/environment";
import { AuthService } from "./auth.service";

@Injectable()
export class NewAccountService {
    
    constructor(
        private httpClient: HttpClient,
        private authService: AuthService
    ) { }

    createNewAccount(newUserValue: NewUserValue): Observable<boolean> {
        return this.httpClient.post(`${environment.apiUrl}/user`, newUserValue).pipe(
            tap((apiResponse) => {
                sessionStorage.setItem('authToken', this.authService.setToken(512));
                sessionStorage.setItem('currentUserId', JSON.parse(JSON.stringify(apiResponse)).id);
                sessionStorage.setItem('ewalletAmount', JSON.parse(JSON.stringify(apiResponse)).amount);
            }),
            map(() => true),
            catchError(() => of(false))
        );
    }
}