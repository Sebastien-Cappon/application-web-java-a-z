import { User } from "./user.model"

export class Transaction {
    
    id!: number;
    date!: string;
    sender!: User;
    receiver!: User;
    amount!: number;
    fee!: number;
    description!: string;
}