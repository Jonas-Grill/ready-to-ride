import {Bson} from "../deps.ts";
import db from "../config/db-connection.ts";
import Stable from "../models/stableModel.ts"
import InvalidIdException from "../exceptions/invalidIdException.ts";

const stables = db.collection<Stable>("stables");

export const createStable = async (stable: Stable) => {
    const id = await stables.insertOne({
        name: stable.name,
        description: stable.description,
        arenas: stable.arenas,
        boxes: stable.boxes,

    });

    return id.toString();
}

export const findStable = async () => {
    return await stables.find({}).toArray();
}

export const findStableById = async (id: string) => {
    if (!Bson.ObjectId.isValid(id)) {
        throw new InvalidIdException();
    }

    return await stables.findOne({
        _id: new Bson.ObjectId(id),
    });
}

export const updateStable = async (stable: Stable) => {
    if (!stable._id) {
        throw new InvalidIdException();
    }

    if (!Bson.ObjectId.isValid(stable._id.toString())) {
        throw new InvalidIdException();
    }

    await stables.updateOne(
        {
            _id: new Bson.ObjectId(stable._id.toString()),
        },
        {
            $set: {
                name: stable.name,
                description: stable.description,
                arenas: stable.arenas,
                boxes: stable.boxes,
            }
        },
    );

    return await findStableById(stable._id.toString());
}

export const deleteStable = async (id: string) => {
    if (!Bson.ObjectId.isValid(id)) {
        throw new InvalidIdException();
    }

    return await stables.deleteOne({
        _id: new Bson.ObjectId(id),
    });
}