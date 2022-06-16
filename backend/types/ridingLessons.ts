// deno-lint-ignore-file no-explicit-any

import {Bson, isNumber} from "../deps.ts";

/* ------------------------------ Base Riding Lesson ------------------------------ */

export interface RidingLessonSchema {
    _id?: Bson.ObjectId;
    trainer?: string;
    booked?: boolean;
    arena: string;
    day: Date;
    startHour: number;
}

export const instanceOfRidingLesson = (object: any): object is RidingLessonSchema => {
    return 'arena' in object && typeof object.arena === "string"
        && 'day' in object
        && 'startHour' in object && isNumber(object.startHour)
}

/* ------------------------------ Create Multiple Riding Lesson ------------------------------ */

export interface CreateMultipleRidingLessonSchema extends RidingLessonSchema{
    startHour: number;
}

export const instanceOfCreateMultipleRidingLesson = (object: any): object is CreateMultipleRidingLessonSchema => {
    return 'arena' in object && typeof object.arena === "string"
        && 'day' in object
        && 'startHour' in object && isNumber(object.startHour)
}
