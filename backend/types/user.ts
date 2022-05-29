import {Bson} from "../deps.ts"
import {instanceOfUserRole, UserRole} from "./userRole.ts";
import {isNumber} from "https://deno.land/std@0.63.0/node/util.ts";
import {instanceOfProficiency, Proficiency} from "./proficiency.ts";
import {Focus, instanceOfFocus} from "./focus.ts";

/* ------------------------------ Name ------------------------------ */

export interface NameSchema {
    firstName: string;
    lastName: string;
}

export const instanceOfName = (object: any): object is BaseUserSchema => {
    return 'firstName' in object && typeof object.firstName === "string"
        && 'lastName' in object && typeof object.lastName === "string"
}

/* ------------------------------ Base User ------------------------------ */

export interface BaseUserSchema {
    _id?: Bson.ObjectId;
    email: string;
    password: string;
    name: NameSchema;
    age: number;
    role: UserRole;
}

export const instanceOfBaseUser = (object: any): object is BaseUserSchema => {
    return 'email' in object && typeof object.email === "string"
        && 'password' in object && typeof object.password === "string"
        && 'name' in object && instanceOfName(object.name)
        && 'age' in object && isNumber(object.age)
        && 'role' in object && instanceOfUserRole(object.role)
}

/* ------------------------------ User ------------------------------ */

export interface UserSchema extends BaseUserSchema{
    weight: number;
    height: number;
    proficiency: Proficiency;
}

export const instanceOfUser = (object: any): object is UserSchema => {
    return 'weight' in object && isNumber(object.weight)
        && 'height' in object && isNumber(object.height)
        && 'proficiency' in object && instanceOfProficiency(object.proficiency)
        && instanceOfBaseUser(object)
}

/* ------------------------------ Trainer ------------------------------ */

export interface TrainerSchema extends BaseUserSchema{
    focus: Focus;
    profilePicture?: string;
    description: string;
    achievements?:	{
        year: number;
        name: string;
    }[];
    certificates?:	{
        year: number;
        name: string;
    }[];
    pictures?: string[];
}

export const instanceOfTrainer = (object: any): object is TrainerSchema => {
    return 'focus' in object && instanceOfFocus(object.focus)
        && 'description' in object && typeof object.description === "string"
        && instanceOfBaseUser(object)
}

export interface RegisterTrainerSchema extends TrainerSchema{
    rolePasscode: string;
}

export const instanceOfRegisterTrainer = (object: any): object is RegisterTrainerSchema => {
    return 'rolePasscode' in object && typeof object.rolePasscode === "string"
        && instanceOfTrainer(object)
}

/* ------------------------------ Admin ------------------------------ */

export interface AdminSchema extends BaseUserSchema{
    rolePasscode: string;
}

export const instanceOfAdmin = (object: any): object is TrainerSchema => {
    return 'rolePasscode' in object && typeof object.rolePasscode === "string"
        && instanceOfBaseUser(object)
}
