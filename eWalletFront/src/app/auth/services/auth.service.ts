import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, map, of, tap } from "rxjs";
import { environment } from "src/app/environments/environment";
import { AuthValue } from "../models/auth.model";
import { User } from "src/app/core/models/user.model";

@Injectable()
export class AuthService {

    constructor(private httpClient: HttpClient) { }

    getUserById(userId: number): Observable<User> {
        return this.httpClient.get<User>(`${environment.apiUrl}/users/login/${userId}`);
    }

    login(authValue: AuthValue): Observable<boolean> {
        return this.httpClient.post(`${environment.apiUrl}/login`, authValue).pipe(
            tap((apiResponse) => {
                sessionStorage.setItem('authToken', this.setToken(512));
                sessionStorage.setItem('currentUserId', JSON.parse(JSON.stringify(apiResponse)).id);
                sessionStorage.setItem('ewalletAmount', JSON.parse(JSON.stringify(apiResponse)).amount);
            }),
            map(() => true),
            catchError(() => of(false))
        );
    }

    isLogged(): boolean {
        return sessionStorage.getItem('currentUserId') ? true : false;
    }

    logout(): boolean {
        sessionStorage.removeItem('authToken');
        sessionStorage.removeItem('currentUserId');
        sessionStorage.removeItem(('ewalletAmount'));
        
        return (sessionStorage.getItem('authToken') == null && sessionStorage.getItem('currentUserId') == null);
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
        let authToken = sessionStorage.getItem('authToken');
        return authToken;
    }
}