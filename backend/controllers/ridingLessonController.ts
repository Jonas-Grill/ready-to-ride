import * as ridingLessonService from "../services/ridingLessonService.ts";
import {Context, helpers, Status} from "../deps.ts";
import {UserRole} from "../types/userRole.ts";
import {instanceOfRidingLesson, RidingLessonSchema} from "../types/ridingLesson.ts";

export const findRidingLesson = async (ctx: Context) => {
    const {
        trainer,
        horses,
        fromDate,
        toDate,
        getPossibleHorseCombinations,
        bookedLessons
    } = helpers.getQuery(ctx, {mergeParams: true});

    const ridingLessons = await ridingLessonService.findRidingLesson(
        trainer,
        horses ? horses.replace(/\s/g, '').split(',') : undefined,
        fromDate,
        toDate,
        toBool(getPossibleHorseCombinations),
        toBool(bookedLessons)
    );

    ctx.response.status = Status.OK;
    ctx.response.body = ridingLessons;
}

export const addRidingLesson = async (ctx: Context) => {
    ctx.assert(ctx.state.currentUser.role === UserRole.TRAINER, Status.Unauthorized, "Your role isn't authorized to access this function")

    ctx.assert(ctx.request.hasBody, Status.BadRequest, "Please provide data");

    const ridingLessonData = await ctx.request.body().value;

    ctx.assert(ridingLessonData, Status.BadRequest, "Please provide data");
    ctx.assert(instanceOfRidingLesson(ridingLessonData), Status.BadRequest, "Please provide valid data");

    const ridingLesson: RidingLessonSchema = await ridingLessonService.addRidingLesson(ridingLessonData, ctx.state.currentUser);

    ctx.response.status = Status.Created;
    ctx.response.body = ridingLesson;
}

/* ------------------------------ Util ------------------------------ */

const toBool = (value: string) => {
    return value ? value.toLowerCase() == 'true' : undefined;
}