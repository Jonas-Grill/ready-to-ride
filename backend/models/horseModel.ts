import {Bson} from "../deps.ts"
import {Proficiency} from "../types/proficiency.ts";
import {Colour, COLOURS} from "../types/colour.ts";
import {Race} from "../types/race.ts";

export default interface HorseModel {
    _id?: Bson.ObjectId;
    name: string;
    height: number;
    race: Race;
    age: number;
    colour: Colour;
    difficultyLevel: Proficiency;
    profilePicture?: string;
    description?: string;
    pictures?: string[];
}