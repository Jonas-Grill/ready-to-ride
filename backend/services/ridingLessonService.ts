import * as ridingLessonRepo from "../repositories/ridingLessonRepo.ts";
import RidingLessonModel from "../models/ridingLessonModel.ts";
import {RidingLessonSchema} from "../types/ridingLessons.ts";

export async function findRidingLesson(trainer?: string, horse?: string, fromDate?: string, toDate?: string, getPossibleRidingLessonCombinations?: boolean, onlyUnbookedLessons?: boolean) {
    return await ridingLessonRepo.findRidingLesson();
}

export const addRidingLesson = async (ridingLesson: R) => {
    const id: string = await ridingLessonRepo.createRidingLesson(horse);

    const newRidingLesson: RidingLessonModel | undefined = await ridingLessonRepo.findRidingLessonById(id)

    if (!newRidingLesson) {
        throw new Error("Error in addRidingLesson Method in horseService.");
    }

    return horseModelToExtendedRidingLesson(newRidingLesson);
}
