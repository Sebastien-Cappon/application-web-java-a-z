import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { User } from "../models/user.model";
import { environment } from "src/app/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class UsersService {

    constructor(private http: HttpClient) {}

    getUsers(): Observable<User[]> {
        return this.http.get<User[]>(`${environment.apiUrl}/users`);
    }
}