import {Bson} from "../deps.ts";
import db from "../config/db-connection.ts";
import User from "../models/userModel.ts"
import InvalidIdException from "../exceptions/invalidIdException.ts";
import {UserRole} from "../types/userRole.ts";

const users = db.collection<User>("users");

export const createUser = async (user: User) => {
    const id = await users.insertOne({
        password: user.password,
        email: user.email,
        name: user.name,
        age: user.age,
        role: user.role,
        //Trainer
        focus: user.focus,
        profilePicture: user.profilePicture,
        achievements: user.achievements,
        certificates: user.certificates,
        pictures: user.pictures,
        // User
        weight: user.weight,
        height: user.height,
        proficiency: user.proficiency,
    });

    return id.toString();
}

export const findUser = async () => {
    return await users.find({}).toArray();
}

export const findUserById = async (id: string) => {
    if (!Bson.ObjectId.isValid(id)) {
        throw new InvalidIdException();
    }

    return await users.findOne({
        _id: new Bson.ObjectId(id),
    });
}

export const findUserByEmail = async (email: string) => {
    return await users.findOne({
        email: email,
    });
}

export const findUserByRole = async (role: UserRole) => {
    return await users.findOne({
        role: role,
    });
}

export const updateUser = async (user: User) => {
    if (!user._id) {
        throw new InvalidIdException();
    }

    if (!Bson.ObjectId.isValid(user._id.toString())) {
        throw new InvalidIdException();
    }

    await users.updateOne(
        {
            _id: new Bson.ObjectId(user._id.toString()),
        },
        {
            $set: {
                password: user.password,
                email: user.email,
                name: user.name,
                age: user.age,
                role: user.role,
                //Trainer
                focus: user.focus,
                profilePicture: user.profilePicture,
                achievements: user.achievements,
                certificates: user.certificates,
                pictures: user.pictures,
                // User
                weight: user.weight,
                height: user.height,
                proficiency: user.proficiency,
            }
        },
    );

    return await getUserById(user._id.toString());
}

export const deleteUser = async (id: string) => {
    if (!Bson.ObjectId.isValid(id)) {
        throw new InvalidIdException();
    }

    return await users.deleteOne({
        _id: new Bson.ObjectId(id),
    });
}