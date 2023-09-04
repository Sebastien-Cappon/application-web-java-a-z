import { User } from "./user.model";

export class Buddy {
    id!: {
        firstUser: User;
        secondUser: User;
    }
}