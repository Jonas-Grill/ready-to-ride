import {Context, create, getNumericDate, Header, Payload, Status, helpers, HttpError} from "../deps.ts";
import {KEY, SIGN_ALG} from ".././config/config.ts";
import * as userService from "../services/userService.ts";
import {
    BaseUserSchema,
    instanceOfAdmin,
    instanceOfBaseUser, instanceOfRegisterTrainer,
    instanceOfUser
} from "../types/user.ts";
import {USER_ROLES, UserRole} from "../types/userRole.ts";
import {FOCUSES} from "../types/focus.ts";
import {PROFINCIES} from "../types/proficiency.ts";

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

export const findUserById = async (ctx: Context) => {
    const id: string = helpers.getQuery(ctx, { mergeParams: true }).id;

    ctx.assert(id, Status.BadRequest, "Please provide an id");

    const user: BaseUserSchema | undefined = await userService.findUserById(id);

    ctx.assert(user, Status.NotFound, `Couldn't find a users with the id ${id}`);

    // deno-lint-ignore no-unused-vars
    const {password, role, _id, ...rest} = user;

    ctx.response.status = Status.Created;
    ctx.response.body = rest;
};

export const findCurrentUser = (ctx: Context) => {
    ctx.response.status = Status.OK;
    ctx.response.body = ctx.state.currentUser;
};

export const login = async (ctx: Context) => {
    ctx.assert(ctx.request.hasBody, Status.BadRequest, "Please provide data");

    const userData = await ctx.request.body().value;

    ctx.assert(userData, Status.BadRequest, "Please provide data");
    ctx.assert('email' in userData, Status.BadRequest, "Please provide an email");
    ctx.assert('password' in userData, Status.BadRequest, "Please provide a password");

    if (!await userService.isUserDataValid(userData)) {
        ctx.response.status = Status.BadRequest;
        ctx.response.body = {
            msg: "Invalid email or password",
        };
        return;
    }

    const payload: Payload = {
        email: userData.email,
        exp: getNumericDate(60 * 60 * 24),
    };

    // Create JWT and send it to user
    const jwt: string = await createToken(payload);

    if (jwt) {
        ctx.response.status = Status.OK;
        ctx.response.body = {
            token: jwt,
        };
    } else {
        ctx.throw(Status.InternalServerError)
    }
}

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
            // ctx.assert(await isUserPasscodeValid(userData.rolePasscode, userData.role), Status.BadRequest, "Invalid passcode");
            break;
        case UserRole.ADMIN:
            ctx.assert(instanceOfAdmin(userData), Status.BadRequest, "Please provide Data in a valid format.");
            // ctx.assert(await isUserPasscodeValid(userData.rolePasscode, userData.role), Status.BadRequest, "Invalid passcode");
            break;
        default:
            throw new HttpError("Invalid user role");
    }

    ctx.response.status = Status.Created;
    ctx.response.body = await userService.addNewUser(userData);
}

export const updateUser = async (ctx: Context) => {
    ctx.assert(ctx.request.hasBody, Status.BadRequest, "Please provide data");

    const user = await ctx.request.body().value;

    ctx.assert(user, Status.BadRequest, "Please provide data");

    ctx.response.status = Status.OK;
    ctx.response.body = await userService.updateUser(user, ctx.state.currentUser.email);
};

/* ------------------------------ Enums ------------------------------ */

export const findUserRoles = (ctx: Context) => {
    ctx.response.status = Status.OK;
    ctx.response.body = USER_ROLES;
};

export const findUserFocuses = (ctx: Context) => {
    ctx.response.status = Status.OK;
    ctx.response.body = FOCUSES;
};

export const findUserProficiencies = (ctx: Context) => {
    ctx.response.status = Status.OK;
    ctx.response.body = PROFINCIES;
};


/* ------------------------------ Util ------------------------------ */

const createToken = async (payload: Payload) => {
    const header: Header = {
        alg: SIGN_ALG,
        typ: "JWT",
    };

    return await create(header, payload, KEY);
};