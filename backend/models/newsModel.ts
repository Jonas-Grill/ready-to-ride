import {Bson} from "../deps.ts"

export default interface HorseModel {
    _id?: Bson.ObjectId;
    caption: string;
    text: string;
    addressees: string;
}