import * as ridingLessonService from "../services/ridingLessonService.ts";
import {Context, helpers, Status} from "../deps.ts";

export const findRidingLesson = async (ctx: Context) => {
    const {trainer, horse, fromDate, toDate, getPossibleHorseCombinations, onlyUnbookedLessons} = helpers.getQuery(ctx, { mergeParams: true });

    const ridingLessons = await ridingLessonService.findRidingLesson(trainer, horse, fromDate, toDate, toBool(getPossibleHorseCombinations), toBool(onlyUnbookedLessons));

    ctx.response.status = Status.OK;
    ctx.response.body = ridingLessons.map((ridingLesson) => {
        return {
            _id: ridingLesson._id,
            trainer: ridingLesson.trainer.name,
            booked: ridingLesson.booked,
            arena: ridingLesson.arena,
            day: ridingLesson.day,
            startHour: ridingLesson.startHour,
        }
    });
}

/* ------------------------------ Util ------------------------------ */

const toBool = (value: string) => {
    return value.toLowerCase() == 'true';
}