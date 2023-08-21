import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, map, of, tap } from "rxjs";
import { environment } from "src/app/environments/environment";
import { AuthValue } from "../models/auth.model";

@Injectable()
export class AuthService {

    constructor(private httpClient: HttpClient) { }

    login(authValue: AuthValue): Observable<boolean> {
        return this.httpClient.post(`${environment.apiUrl}/login`, authValue).pipe(
            tap((apiResponse) => {
                sessionStorage.setItem("authToken", this.setToken(512));
                sessionStorage.setItem("currentUser", JSON.stringify(apiResponse));
                sessionStorage.setItem("currentUserId", JSON.parse(sessionStorage.getItem("currentUser")!).id);
            }),
            map(() => true),
            catchError(() => of(false))
        );
    }

    isLogged(): boolean {
        return sessionStorage.getItem("currentUserId") ? true : false;
    }

    logout(): boolean {
        sessionStorage.removeItem("authToken");
        sessionStorage.removeItem("currentUser");
        sessionStorage.removeItem("currentUserId");
        
        return (sessionStorage.getItem("currentUser") == null && sessionStorage.getItem("authToken") == null && sessionStorage.getItem("currentUserId") == null);
    }

    setToken(length: number): string {
        const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        const randomValues = new Uint32Array(length);
        let result = '';

        window.crypto.getRandomValues(randomValues);
        randomValues.forEach((value) => { result += characters.charAt(value % characters.length) });

        return result;
    }

    getToken() {
        let authToken = sessionStorage.getItem("authToken");
        return authToken;
    }
}