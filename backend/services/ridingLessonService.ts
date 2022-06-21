import * as ridingLessonRepo from "../repositories/ridingLessonRepo.ts";
import RidingLessonModel from "../models/ridingLessonModel.ts";
import {RidingLessonSchema} from "../types/ridingLessons.ts";
import UserModel from "../models/userModel.ts";

// deno-lint-ignore no-unused-vars
export async function findRidingLesson(trainer?: string, horse?: string, fromDate?: string, toDate?: string, getPossibleRidingLessonCombinations?: boolean, onlyUnbookedLessons?: boolean) {


    return await ridingLessonRepo.findRidingLesson();
}

export const addRidingLesson = async (ridingLesson: RidingLessonSchema, currentUser: UserModel) => {
    const id: string = await ridingLessonRepo.createRidingLesson(
        {
            trainer: {
                name: `${currentUser.name.firstName} ${currentUser.name.lastName}`,
                id: currentUser._id?.toString() || "Error"
            },
            booked: false,
            arena: ridingLesson.arena,
            day: ridingLesson.day,
            startHour: ridingLesson.startHour,
        }
    );

    const newRidingLesson: RidingLessonModel | undefined = await ridingLessonRepo.findRidingLessonById(id)

    if (!newRidingLesson) {
        throw new Error("Error in addRidingLesson Method in horseService.");
    }

    return ridingLessonModelToRidingLesson(newRidingLesson);
}

/* ------------------------------ Util ------------------------------ */

const ridingLessonModelToRidingLesson = (ridingLesson: RidingLessonModel): RidingLessonSchema => {
    return {
        _id: ridingLesson._id,
        trainer: ridingLesson.trainer.name,
        booked: ridingLesson.booked,
        arena: ridingLesson.arena,
        day: ridingLesson.day,
        startHour: ridingLesson.startHour,
    };
}
