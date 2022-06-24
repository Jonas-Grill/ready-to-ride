import {Bson} from "../deps.ts";
import db from "../config/db-connection.ts";
import RidingLesson from "../models/ridingLessonModel.ts"
import InvalidIdException from "../exceptions/invalidIdException.ts";
import {addDays, formatDate} from "../util/dateUtil.ts";

const ridingLessons = db.collection<RidingLesson>("ridingLessons");

export const createRidingLesson = async (ridingLesson: RidingLesson) => {
    const id = await ridingLessons.insertOne({
        trainer: ridingLesson.trainer,
        booked: ridingLesson.booked,
        bookerEmail: ridingLesson.bookerEmail,
        arena: ridingLesson.arena,
        day: ridingLesson.day,
        startHour: ridingLesson.startHour,
        horse: ridingLesson.horse,
    });

    return id.toString();
}

export const findRidingLesson = async () => {
    return await ridingLessons.find({
        day: {
            $gte: addDays(0),
            $lt: addDays(7)
        }
    }).toArray();
}

export async function findRidingLessonByDay(fromDate: string, toDate: string) {
    return await ridingLessons.find({
        day: {
            $gte: fromDate,
            $lt: toDate
        }
    }).toArray();
}

export async function findUnbookedRidingLessonByDay(fromDate: string, toDate: string) {
    return await ridingLessons.find({
        booked: false,
        day: {
            $gte: fromDate,
            $lt: toDate
        }
    }).toArray();
}

export async function findBookedRidingLessonsByDay(fromDate: string, toDate: string) {
    return await ridingLessons.find({
        booked: true,
        day: {
            $gte: fromDate,
            $lt: toDate
        }
    }).toArray();
}

export async function findBookedRidingLessonsByTrainerIdAndHorseIdsAndDay(trainerId: string, horseIds: string[], fromDate: string, toDate: string) {
    return await ridingLessons.find({
        "trainer.id": trainerId,
        "horse.id": {$in: horseIds},
        booked: true,
        day: {
            $gte: fromDate,
            $lt: toDate
        }
    }).toArray();
}

export async function findBookedRidingLessonsByHorseIdAndDay(horseIds: string[], fromDate: string, toDate: string) {
    return await ridingLessons.find({
        "horse.id": {$in: horseIds},
        booked: true,
        day: {
            $gte: fromDate,
            $lt: toDate
        }
    }).toArray();
}

export async function findBookedRidingLessonsByTrainerIdAndDay(trainerId: string, fromDate: string, toDate: string) {
    return await ridingLessons.find({
        "trainer.id": trainerId,
        booked: true,
        day: {
            $gte: fromDate,
            $lt: toDate
        }
    }).toArray();
}

export const findUnbookedRidingLessonByTrainerAndDay = async (trainerId: string, fromDate: string, toDate: string) => {
    return await ridingLessons.find({
        "trainer.id": trainerId,
        booked: false,
        day: {
            $gte: fromDate,
            $lt: toDate
        }
    }).toArray();
}

export const findRidingLessonById = async (id: string) => {
    if (!Bson.ObjectId.isValid(id)) {
        throw new InvalidIdException();
    }

    return await ridingLessons.findOne({
        _id: new Bson.ObjectId(id),
    });
}

export const findRidingLessonByTrainerId = async (trainerId: string) => {
    return await ridingLessons.find({
        "trainer.id": trainerId,
    }).toArray();
}

export const findRidingLessonByBookerEmail = async (bookerEmail: string) => {
    return await ridingLessons.findOne({
        bookerEmail: bookerEmail,
    });
}

export const updateRidingLesson = async (ridingLesson: RidingLesson) => {
    if (!ridingLesson._id) {
        throw new InvalidIdException();
    }

    if (!Bson.ObjectId.isValid(ridingLesson._id.toString())) {
        throw new InvalidIdException();
    }

    await ridingLessons.updateOne(
        {
            _id: new Bson.ObjectId(ridingLesson._id.toString()),
        },
        {
            $set: {
                trainer: ridingLesson.trainer,
                booked: ridingLesson.booked,
                bookerEmail: ridingLesson.bookerEmail,
                arena: ridingLesson.arena,
                day: ridingLesson.day,
                startHour: ridingLesson.startHour,
                horse: ridingLesson.horse,
            }
        },
    );

    return await findRidingLessonById(ridingLesson._id.toString());
}

export const deleteRidingLesson = async (id: string) => {
    if (!Bson.ObjectId.isValid(id)) {
        throw new InvalidIdException();
    }

    return await ridingLessons.deleteOne({
        _id: new Bson.ObjectId(id),
    });
}