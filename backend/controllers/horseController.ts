import {Context, helpers, Status} from "../deps.ts";
import * as horseService from "../services/horseService.ts";
import {PROFINCIES} from "../types/proficiency.ts";
import {ExtendedHorseSchema, HorseSchema, instanceOfHorse} from "../types/horse.ts";
import {RACES} from "../types/race.ts";
import {COLOURS} from "../types/colour.ts";
import {UserRole} from "../types/userRole.ts";

export const findHorse = async (ctx: Context) => {
    ctx.response.status = Status.OK;
    ctx.response.body = (await horseService.findHorse()).map((horse) => {
        return {
            _id: horse._id,
            name: horse.name,
            height: horse.height,
            race: horse.race,
            age: horse.age,
            colour: horse.colour,
            difficultyLevel: horse.difficultyLevel,
            profilePicture: horse.profilePicture,
        }
    });
}

export const findHorseById = async (ctx: Context) => {
    const id: string = helpers.getQuery(ctx, { mergeParams: true }).id;

    ctx.assert(id, Status.BadRequest, "Please provide an id");

    const horse: ExtendedHorseSchema | undefined = await horseService.findHorseById(id);

    ctx.assert(horse, Status.NotFound, `Couldn't find a horse with the id ${id}`);

    ctx.response.status = Status.Created;
    ctx.response.body = horse;
};

export const addHorse = async (ctx: Context) => {
    ctx.assert(ctx.state.currentUser.role === UserRole.ADMIN, Status.Unauthorized, "Your role isn't authorized to access this function")

    ctx.assert(ctx.request.hasBody, Status.BadRequest, "Please provide data");

    const horseData = await ctx.request.body().value;

    ctx.assert(horseData, Status.BadRequest, "Please provide data");
    ctx.assert(instanceOfHorse(horseData), Status.BadRequest, "Please provide valid data");

    const horse: HorseSchema = await horseService.addHorse(horseData);

    ctx.response.status = Status.Created;
    ctx.response.body = {
        _id: horse._id,
        name: horse.name,
        height: horse.height,
        race: horse.race,
        age: horse.age,
        colour: horse.colour,
        difficultyLevel: horse.difficultyLevel,
        profilePicture: horse.profilePicture,
    };
}

export const updateHorse = async (ctx: Context) => {
    ctx.assert(ctx.state.currentUser.role === UserRole.ADMIN, Status.Unauthorized, "Your role isn't authorized to access this function")

    const id: string = helpers.getQuery(ctx, { mergeParams: true }).id;

    ctx.assert(id, Status.BadRequest, "Please provide an id");
    ctx.assert(ctx.request.hasBody, Status.BadRequest, "Please provide data");

    const horseData = await ctx.request.body().value;

    ctx.assert(horseData, Status.BadRequest, "Please provide data");

    const horse: ExtendedHorseSchema | undefined = await horseService.updateHorse(horseData, id);

    ctx.assert(horse, Status.NotFound, `Couldn't find a users with the id ${id}`);

    ctx.response.status = Status.Created;
    ctx.response.body = horse;
};

export const deleteHorse = async (ctx: Context) => {
    ctx.assert(ctx.state.currentUser.role === UserRole.ADMIN, Status.Unauthorized, "Your role isn't authorized to access this function")

    const id: string = helpers.getQuery(ctx, { mergeParams: true }).id;

    ctx.assert(id, Status.BadRequest, "Please provide an id");

    await horseService.deleteHorse(id);

    ctx.response.status = Status.NoContent;
};

/* ------------------------------ Enums ------------------------------ */

export const findHorseRaces = (ctx: Context) => {
    ctx.response.status = Status.OK;
    ctx.response.body = RACES;
};

export const findHorseColours = (ctx: Context) => {
    ctx.response.status = Status.OK;
    ctx.response.body = COLOURS;
};

export const findHorseLevels = (ctx: Context) => {
    ctx.response.status = Status.OK;
    ctx.response.body = PROFINCIES;
};