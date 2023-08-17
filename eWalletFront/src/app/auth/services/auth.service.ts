import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, map, of } from "rxjs";
import { environment } from "src/app/environments/environment";

@Injectable({
    providedIn: 'root' /* There is a LoginService provider in auth.module.ts. We may delete this provideIn. */
})
export class AuthService {
    private token!: string;

    constructor() { }

    ngOnInit() {
        
    }

    setToken(): void {
        this.token = 'MyFakeToken';
    }

    getToken(): string {
        return this.token;
    }
}