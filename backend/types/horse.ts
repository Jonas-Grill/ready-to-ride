// deno-lint-ignore-file no-explicit-any

import {Bson, isNumber} from "../deps.ts";
import {instanceOfRace, Race} from "./race.ts";
import {Colour, instanceOfColour} from "./colour.ts";
import {instanceOfProficiency, Proficiency} from "./proficiency.ts";

/* ------------------------------ Horse ------------------------------ */

export interface HorseSchema {
    _id?: Bson.ObjectId;
    name: string;
    height: number;
    race: Race;
    age: number;
    colour: Colour;
    difficultyLevel: Proficiency;
    profilePicture?: string;
}

export const instanceOfHorse = (object: any): object is HorseSchema => {
    return 'name' in object && typeof object.name === "string"
        && 'height' in object && isNumber(object.height)
        && 'race' in object && instanceOfRace(object.race)
        && 'age' in object && isNumber(object.age)
        && 'colour' in object && instanceOfColour(object.colour)
        && 'difficultyLevel' in object && instanceOfProficiency(object.difficultyLevel)
}

/* ------------------------------ Extended Horse ------------------------------ */

export interface ExtendedHorseSchema extends HorseSchema{
    description: string;
    pictures?: string[];
}

export const instanceOfExtendedHorse = (object: any): object is ExtendedHorseSchema => {
    return 'description' in object && typeof object.description === "string"
    && instanceOfHorse(object)
}