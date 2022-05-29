import * as userRepo from "../repositories/userRepo.ts";
import invalidDataException from "../exceptions/invalidDataException.ts";
import {BaseUserSchema, TrainerSchema, UserSchema} from "../types/user.ts";
import {UserRole} from "../types/userRole.ts";
import UserModel from "../models/userModel.ts";
import {Focus} from "../types/focus.ts";
import {Proficiency} from "../types/proficiency.ts";

export const findTrainer = async () => {
    return (await userRepo.findUserByRole(UserRole.TRAINER)).map(userModelToTrainer);
}

export const findUserByEmail = (email: string) => {
    return userRepo.findUserByEmail(email);
}

export const isUserDataValid = async (userData: { email: string; password: string }) => {
    const user: UserModel | undefined = await userRepo.findUserByEmail(userData.email);

    if (!user) {
        return false;
    } else return userData.password === user.password;
}

export const createNewUser = async (userData: BaseUserSchema) => {
    if ((await userRepo.findUserByEmail(userData.email))) {
        throw new invalidDataException("This email is already in use");
    }

    // password hashing disabled for testing
    // userData.password = await bcrypt.hash(
    //     userData.password,
    //     await bcrypt.genSalt(8),
    // );

    const id: string = await userRepo.createUser(userData);

    return await userRepo.findUserById(id);
}

/* ------------------------------ Util ------------------------------ */

const userModelToBaseUser = (user: UserModel): BaseUserSchema => {
    return {
        password: user.password,
        email: user.email,
        name: user.name,
        age: user.age,
        role: user.role,
    };
}

function userModelToUser(user: UserModel): UserSchema {
    return {
        ...userModelToBaseUser(user),
        weight: user.weight || 0,
        height: user.height || 0,
        proficiency: user.proficiency || Proficiency.INTERNAL_ERROR,
    };
}

function userModelToTrainer(user: UserModel): TrainerSchema {
    return {
        ...userModelToBaseUser(user),
        focus: user.focus || Focus.INTERNAL_ERROR,
        profilePicture: user.profilePicture,
        description: user.description || "500",
        achievements: user.achievements,
        certificates: user.certificates,
        pictures: user.pictures,
    };
}

function userModelToAdmin(user: UserModel): BaseUserSchema {
    return {
        ...userModelToBaseUser(user),
    };
}