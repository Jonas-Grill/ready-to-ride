import {Bson} from "../deps.ts";

export default interface RidingLessonModel {
    _id?: Bson.ObjectId;
    trainerId: string;
    trainerName: string;
    booked: boolean;
    bookerEmail: string;
    arena: string;
    day: Date;
    startHour: number;
}