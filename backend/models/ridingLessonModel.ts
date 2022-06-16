import {Bson} from "../deps.ts";

export default interface RidingLessonModel {
    _id?: Bson.ObjectId;
    trainer: string;
    booked: boolean;
    booker: string;
    arena: string;
    day: Date;
    startHour: number;
}