import {Bson} from "../deps.ts"
import {UserRole} from "../types/userRole.ts";
import {Focus} from "../types/focus.ts";
import {Proficiency} from "../types/proficiency.ts";
import {NameSchema} from "../types/user.ts";

export default interface UserModel {
    // Base User
    _id?: Bson.ObjectId;
    email: string;
    password: string;
    name: NameSchema;
    age: number;
    role: UserRole;
    //Trainer
    focus?: Focus;
    profilePicture?: string;
    description?: string;
    achievements?:	{
        year: number;
        name: string;
    }[];
    certificates?:	{
        year: number;
        name: string;
    }[];
    pictures?: string[];
    // User
    weight?: number;
    height?: number;
    proficiency?: Proficiency;
}