// deno-lint-ignore-file no-explicit-any

import {Bson} from "../deps.ts";

/* ------------------------------ News ------------------------------ */

export interface NewsSchema {
    _id?: Bson.ObjectId;
    caption: string;
    text: string;
    addressees: string;
}

export const instanceOfNews = (object: any): object is NewsSchema => {
    return 'caption' in object && typeof object.caption === "string"
        && 'text' in object && typeof object.text === "string"
        && 'addressees' in object && typeof object.addressees === "string"
}