import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { User } from "src/app/core/models/user.model";
import { environment } from "src/app/environments/environment";

@Injectable()
export class UsersService {

    constructor(private httpClient: HttpClient) { }

    getUserWalletById(userId: number) {
        return this.httpClient.get<User>(`${environment.apiUrl}/users/amount/${userId}`);
    }
}