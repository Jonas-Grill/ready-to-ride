import {Bson} from "../deps.ts";

export default interface RidingLessonModel {
    _id?: Bson.ObjectId;
    trainer: {
        name: string;
        id: string;
    };
    booked: boolean;
    bookerEmail?: string;
    arena: string;
    day: string;
    startHour: number;
    horse?: {
        name: string;
        id: string;
    };
}