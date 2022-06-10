import {Bson} from "../deps.ts"
import {Proficiency} from "../types/proficiency.ts";
import {Colour} from "../types/colour.ts";

export default interface HorseModel {
    _id?: Bson.ObjectId;
    name: string;
    age: number;
    colour: Colour;
    difficultyLevel: Proficiency;
    profilePicture?: string;
    description?: string;
    pictures?: string[];
}