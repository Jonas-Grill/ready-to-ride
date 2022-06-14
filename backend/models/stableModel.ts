import {ArenaSchema, BoxSchema} from "../types/stable.ts";
import {Bson} from "../deps.ts";

export default interface StableSchema {
    _id?: Bson.ObjectId;
    name: string;
    description: string;
    arenas: ArenaSchema[];
    boxes: BoxSchema[];
    adminPasscode: string;
    trainerPasscode: string;
}