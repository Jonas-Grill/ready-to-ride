import * as userRepo from "../repositories/userRepo.ts";
import invalidDataException from "../exceptions/invalidDataException.ts";
import {BaseUserSchema, TrainerSchema, UserSchema} from "../types/user.ts";
import {UserRole} from "../types/userRole.ts";
import UserModel from "../models/userModel.ts";
import {Focus} from "../types/focus.ts";
import {Proficiency} from "../types/proficiency.ts";
import {findStable} from "./stableService.ts";

export const findTrainer = async () => {
    return (await userRepo.findUserByRole(UserRole.TRAINER)).map(userModelToTrainer);
}

export const findUserByEmail = (email: string) => {
    return userRepo.findUserByEmail(email);
}

export const findUserById = (id: string) => {
    return userRepo.findUserById(id);
}

export const isUserDataValid = async (userData: { email: string; password: string }) => {
    const user: UserModel | undefined = await userRepo.findUserByEmail(userData.email);

    if (!user) {
        return false;
    } else return userData.password === user.password;
}

export const isUserPasscodeValid = async (passcode: string, role: UserRole) => {
    if (role === UserRole.USER) {
        return true;
    } else if (role === UserRole.TRAINER) {
        return (await findStable()).trainerPasscode === passcode;
    } else if (role === UserRole.ADMIN) {
        return (await findStable()).adminPasscode === passcode;
    } else {
        return false;
    }
}



export const addNewUser = async (userData: BaseUserSchema) => {
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

// deno-lint-ignore no-explicit-any
export const updateUser = async (user: any, email: string) => {
    const oldUser: UserModel | undefined = await userRepo.findUserByEmail(email);

    if (!(oldUser)) {
        throw new invalidDataException("A user with this email does not exist");
    }

    return await userRepo.updateUser({
        _id: oldUser._id,
        password: user.password || oldUser.password,
        email: user.email || oldUser.email,
        name: user.name || oldUser.name,
        age: user.age || oldUser.age,
        role: oldUser.role,
        //Trainer
        focus: user.focus || oldUser.focus,
        profilePicture: user.profilePicture || oldUser.profilePicture,
        description: user.description || oldUser.description,
        achievements: user.achievements || oldUser.achievements,
        certificates: user.certificates || oldUser.certificates,
        pictures: user.pictures || oldUser.pictures,
        // User
        weight: user.weight || oldUser.weight,
        height: user.height || oldUser.height,
        proficiency: user.proficiency || oldUser.proficiency,
    });
}

/* ------------------------------ Util ------------------------------ */

const userModelToBaseUser = (user: UserModel): BaseUserSchema => {
    return {
        _id: user._id,
        password: user.password,
        email: user.email,
        name: user.name,
        age: user.age,
        role: user.role,
    };
}

// deno-lint-ignore no-unused-vars
function userModelToUser(user: UserModel): UserSchema {
    return {
        ...userModelToBaseUser(user),
        weight: user.weight || 0,
        height: user.height || 0,
        proficiency: user.proficiency || Proficiency.NONE,
    };
}

function userModelToTrainer(user: UserModel): TrainerSchema {
    return {
        ...userModelToBaseUser(user),
        focus: user.focus || Focus.NONE,
        profilePicture: user.profilePicture,
        description: user.description || "500",
        achievements: user.achievements,
        certificates: user.certificates,
        pictures: user.pictures,
    };
}

// deno-lint-ignore no-unused-vars
function userModelToAdmin(user: UserModel): BaseUserSchema {
    return {
        ...userModelToBaseUser(user),
    };
}