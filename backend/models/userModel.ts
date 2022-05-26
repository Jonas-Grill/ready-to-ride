import {Bson} from "../deps.ts"
import {UserRole} from "../types/userRole.ts";
import {Focus} from "../types/focus.ts";
import {Proficiency} from "../types/proficiency.ts";

export default interface UserSchema {
    // Base User
    _id?: Bson.ObjectId;
    email: string;
    password: string;
    name: {
        firstName: string;
        lastName: string;
    };
    age: number;
    role: UserRole;
    //Trainer
    focus?: Focus;
    profilePicture?: string;
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