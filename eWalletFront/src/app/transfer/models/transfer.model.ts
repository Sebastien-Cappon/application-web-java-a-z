import { User } from "src/app/core/models/user.model";

export class TransferValue {
    senderId!: number;
    receiver!: User;
    amount!: number;
    comment!: string;
}