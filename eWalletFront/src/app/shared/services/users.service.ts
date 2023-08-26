import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, catchError, map, of } from "rxjs";
import { User } from "../../core/models/user.model";
import { environment } from "src/app/environments/environment";

@Injectable()
export class UsersService {

    constructor(private httpClient: HttpClient) { }

    getUsers(): Observable<User[]> {
        return this.httpClient.get<User[]>(`${environment.apiUrl}/users`);
    }

}