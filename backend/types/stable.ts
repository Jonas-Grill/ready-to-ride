// deno-lint-ignore-file no-explicit-any

import {Bson, isNumber} from "../deps.ts";
import {instanceOfRace} from "./race.ts";
import {Colour, instanceOfColour} from "./colour.ts";
import {instanceOfProficiency, Proficiency} from "./proficiency.ts";

/* ------------------------------ Box ------------------------------ */

export interface BoxSchema {
    name: string;
    price: number;
    size: number;
    count: number;
}

export const instanceOfBox = (object: any): object is BoxSchema => {
    return 'name' in object && typeof object.name === "string"
        && 'price' in object && isNumber(object.price)
        && 'size' in object && isNumber(object.size)
        && 'count' in object && isNumber(object.count)
}

/* ------------------------------ Arena ------------------------------ */

export interface ArenaSchema {
    name: string;
    size: number;
}

export const instanceOfArena = (object: any): object is ArenaSchema => {
    return 'name' in object && typeof object.name === "string"
        && 'size' in object && isNumber(object.size)
}

/* ------------------------------ Stable ------------------------------ */

export interface StableSchema {
    _id?: Bson.ObjectId;
    name: string;
    description: string;
    arenas: ArenaSchema[];
    boxes: BoxSchema[];
}

export const instanceOfStable = (object: any): object is StableSchema => {
    return 'name' in object && typeof object.name === "string"
        && 'description' in object && typeof object.description === "string"
        && 'arenas' in object && object.arenas.reduce((previousValue: any, currentValue: any) => {
            return previousValue && instanceOfArena(currentValue);
        }, true)
        && 'boxes' in object && object.boxes.reduce((previousValue: any, currentValue: any) => {
            return previousValue && instanceOfBox(currentValue);
        }, true)
}

/* ------------------------------ Extended Stable ------------------------------ */

export interface ExtendedStableSchema {
    _id?: Bson.ObjectId;
    name: string;
    description: string;
    arenas: ArenaSchema[];
    boxes: BoxSchema[];
    adminPasscode: string;
    trainerPasscode: string;
}

export const instanceOfExtendedStable = (object: any): object is ExtendedStableSchema => {
    return 'name' in object && typeof object.name === "string"
        && 'description' in object && typeof object.description === "string"
        && 'arenas' in object && object.arenas.reduce((previousValue: any, currentValue: any) => {
            return previousValue && instanceOfArena(currentValue);
        }, true)
        && 'boxes' in object && object.boxes.reduce((previousValue: any, currentValue: any) => {
            return previousValue && instanceOfBox(currentValue);
        }, true)
        && 'adminPasscode' in object && typeof object.adminPasscode === "string"
        && 'trainerPasscode' in object && typeof object.trainerPasscode === "string"
}