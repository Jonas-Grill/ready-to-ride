import * as ridingLessonService from "../services/ridingLessonService.ts";
import {Context, helpers, Status} from "../deps.ts";
import {UserRole} from "../types/userRole.ts";
import {
    instanceOfCreateMultipleRidingLesson,
    instanceOfRidingLesson,
    RidingLessonSchema
} from "../types/ridingLesson.ts";
import {doesArenaExist} from "../services/stableService.ts";

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

export const findRidingLessonsByArenaAndDay = async (ctx: Context) => {
    const {
        fromDate,
        toDate,
        name,
    } = helpers.getQuery(ctx, {mergeParams: true});

    ctx.assert(name, Status.BadRequest, "Missing arena name");
    ctx.assert(doesArenaExist(name), Status.BadRequest, "Arena does not exist");

    const ridingLessons = await ridingLessonService.findRidingLessonsByArenaAndDay(
        name,
        fromDate,
        toDate
    );

    ctx.response.status = Status.OK;
    ctx.response.body = ridingLessons;
}

export const findRidingLessonsByUser = async (ctx: Context) => {

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

export const addMultipleRidingLessons = async (ctx: Context) => {
    ctx.assert(ctx.state.currentUser.role === UserRole.TRAINER, Status.Unauthorized, "Your role isn't authorized to access this function")

    ctx.assert(ctx.request.hasBody, Status.BadRequest, "Please provide data");

    const ridingLessonsData = await ctx.request.body().value;

    ctx.assert(ridingLessonsData, Status.BadRequest, "Please provide data");
    ctx.assert(instanceOfCreateMultipleRidingLesson(ridingLessonsData), Status.BadRequest, "Please provide valid data");
    ctx.assert(ridingLessonsData.endHour - ridingLessonsData.startHour >= 1, Status.BadRequest, "Please provide at least one lesson");

    const ridingLessons: RidingLessonSchema[] = await ridingLessonService.addMultipleRidingLessons(ridingLessonsData, ctx.state.currentUser);

    ctx.response.status = Status.Created;
    ctx.response.body = ridingLessons;
}

export const bookRidingLesson = async (ctx: Context) => {
    const {id} = helpers.getQuery(ctx, {mergeParams: true});

    ctx.assert(id, Status.BadRequest, "Please provide a valid id");

    ctx.assert(ctx.request.hasBody, Status.BadRequest, "Please provide data");

    const bookingData = await ctx.request.body().value;

    ctx.assert(bookingData, Status.BadRequest, "Please provide data");
    ctx.assert(bookingData.horseId, Status.BadRequest, "Please provide valid data");

    bookingData.lessonId = id;

    await ridingLessonService.bookRidingLesson(bookingData, ctx.state.currentUser);

    ctx.response.status = Status.Accepted;
}

export const cancelRidingLesson = async (ctx: Context) => {
    ctx.assert(ctx.state.currentUser.role === UserRole.TRAINER, Status.Unauthorized, "Your role isn't authorized to access this function")

    const {id} = helpers.getQuery(ctx, {mergeParams: true});

    ctx.assert(id, Status.BadRequest, "Please provide a valid id");

    await ridingLessonService.cancelRidingLesson(id, ctx.state.currentUser);

    ctx.response.status = Status.Accepted;
}

/* ------------------------------ Util ------------------------------ */

const toBool = (value: string) => {
    return value ? value.toLowerCase() == 'true' : undefined;
}