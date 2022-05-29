import {Context, create, getNumericDate, Header, Payload, Request, Response, State, Status,} from "../deps.ts";
import {KEY, SIGN_ALG} from ".././config/config.ts";
import * as userService from "../services/userService.ts";
import {
    BaseUserSchema,
    instanceOfAdmin,
    instanceOfBaseUser, instanceOfRegisterTrainer,
    instanceOfTrainer,
    instanceOfUser,
    TrainerSchema
} from "../types/user.ts";
import {UserRole} from "../types/userRole.ts";
import UserModel from "../models/userModel.ts";

export const registration = async (ctx: Context) => {
    ctx.assert(ctx.request.hasBody, Status.BadRequest, "Please provide data");

    const userData = await ctx.request.body().value;

    ctx.assert(userData, Status.BadRequest, "Please provide data");
    ctx.assert(instanceOfBaseUser(userData), Status.BadRequest, "Please provide Data in a valid format.");

    switch (userData.role) {
        case UserRole.USER:
            ctx.assert(instanceOfUser(userData), Status.BadRequest, "Please provide Data in a valid format.");
            break;
        case UserRole.TRAINER:
            ctx.assert(instanceOfRegisterTrainer(userData), Status.BadRequest, "Please provide Data in a valid format.");
            break;
        case UserRole.ADMIN:
            ctx.assert(instanceOfAdmin(userData), Status.BadRequest, "Please provide Data in a valid format.");
            break;
    }

    ctx.response.status = Status.Created;
    ctx.response.body = await userService.createNewUser(userData);
}

export const findTrainer = async (ctx: Context) => {
    ctx.response.status = Status.OK;
    ctx.response.body = (await userService.findTrainer()).map((user) => {
        return {
            _id: user._id,
            email: user.email,
            name: user.name,
            age: user.age,
            focus: user.focus,
            profilePicture: user.profilePicture,
        }
    });
}