import {Bson} from "../deps.ts"

export default interface UserSchema {
    _id?: Bson.ObjectId;
    password: string;
    eMail: string;
}

export const instanceOfUser = (object: any): object is UserSchema => {
    return 'username' in object
        && 'password' in object
        && 'eMail' in object
}