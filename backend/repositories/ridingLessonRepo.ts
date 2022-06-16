import {Bson} from "../deps.ts";
import db from "../config/db-connection.ts";
import RidingLesson from "../models/ridingLessonModel.ts"
import InvalidIdException from "../exceptions/invalidIdException.ts";

const ridingLessons = db.collection<RidingLesson>("ridingLessons");

export const createRidingLesson = async (ridingLesson: RidingLesson) => {
    const id = await ridingLessons.insertOne({
        arena: "",
        booked: false,
        bookerEmail: "",
        day: undefined,
        startHour: 0,
        trainerId: "",
        trainerName: "",

    });

    return id.toString();
}

export const findRidingLesson = async () => {
    return await ridingLessons.find({}).toArray();
}

export const findRidingLessonById = async (id: string) => {
    if (!Bson.ObjectId.isValid(id)) {
        throw new InvalidIdException();
    }

    return await ridingLessons.findOne({
        _id: new Bson.ObjectId(id),
    });
}

export const findRidingLessonByName = async (name: string) => {
    return await ridingLessons.findOne({
        name: name,
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
                name: ridingLesson.name,
                height: ridingLesson.height,
                race: ridingLesson.race,
                age: ridingLesson.age,
                colour: ridingLesson.colour,
                difficultyLevel: ridingLesson.difficultyLevel,
                profilePicture: ridingLesson.profilePicture,
                description: ridingLesson.description,
                pictures: ridingLesson.pictures
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