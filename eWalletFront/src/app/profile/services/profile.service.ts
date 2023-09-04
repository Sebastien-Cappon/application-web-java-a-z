import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, delay, map, of } from "rxjs";
import { environment } from "src/app/environments/environment";
import { ProfileValue } from "../models/profile.model";
import { User } from "src/app/core/models/user.model";

@Injectable()
export class ProfileService {
    
    constructor(private httpClient: HttpClient) { }

    getUserById(userId: number): Observable<User> {
        return this.httpClient.get<User>(`${environment.apiUrl}/users/profile/${userId}`);
    }

    updateProfile(userId: number, formValue: ProfileValue): Observable<boolean> {
        return this.httpClient.put(`${environment.apiUrl}/users/${userId}/profile`, formValue).pipe(
            map(() => true),
            catchError(() => of(false))
        );
    }

    disableUserById(userId: number): Observable<boolean> {
        return this.httpClient.put(`${environment.apiUrl}/users/${userId}/active`, false).pipe(
            map(() => true),
            catchError(() => of(false))
        );
    }
}