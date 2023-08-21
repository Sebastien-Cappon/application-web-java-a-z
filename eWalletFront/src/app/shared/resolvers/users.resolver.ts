import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from "@angular/router";
import { User } from "../../core/models/user.model";
import { UsersService } from "../services/users.service";
import { Observable } from "rxjs";

@Injectable()
export class UsersResolver implements Resolve<User[]> {

    constructor(private usersService: UsersService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User[]> {
        return this.usersService.getUsers();
    }
}