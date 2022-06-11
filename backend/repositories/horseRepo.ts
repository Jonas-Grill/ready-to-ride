import {Bson} from "../deps.ts";
import db from "../config/db-connection.ts";
import Horse from "../models/horseModel.ts"
import InvalidIdException from "../exceptions/invalidIdException.ts";

const horses = db.collection<Horse>("horses");

export const createHorse = async (horse: Horse) => {
    const id = await horses.insertOne({
        name: horse.name,
        height: horse.height,
        race: horse.race,
        age: horse.age,
        colour: horse.colour,
        difficultyLevel: horse.difficultyLevel,
        profilePicture: horse.profilePicture,
        description: horse.description,
        pictures: horse.pictures
    });

    return id.toString();
}

export const findHorse = async () => {
    return await horses.find({}).toArray();
}

export const findHorseById = async (id: string) => {
    if (!Bson.ObjectId.isValid(id)) {
        throw new InvalidIdException();
    }

    return await horses.findOne({
        _id: new Bson.ObjectId(id),
    });
}

export const findHorseByName = async (name: string) => {
    return await horses.findOne({
        name: name,
    });
}

export const updateHorse = async (horse: Horse) => {
    if (!horse._id) {
        throw new InvalidIdException();
    }

    if (!Bson.ObjectId.isValid(horse._id.toString())) {
        throw new InvalidIdException();
    }

    await horses.updateOne(
        {
            _id: new Bson.ObjectId(horse._id.toString()),
        },
        {
            $set: {
                name: horse.name,
                height: horse.height,
                race: horse.race,
                age: horse.age,
                colour: horse.colour,
                difficultyLevel: horse.difficultyLevel,
                profilePicture: horse.profilePicture,
                description: horse.description,
                pictures: horse.pictures
            }
        },
    );

    return await findHorseById(horse._id.toString());
}

export const deleteHorse = async (id: string) => {
    if (!Bson.ObjectId.isValid(id)) {
        throw new InvalidIdException();
    }

    return await horses.deleteOne({
        _id: new Bson.ObjectId(id),
    });
}