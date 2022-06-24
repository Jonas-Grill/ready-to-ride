import * as ridingLessonRepo from "../repositories/ridingLessonRepo.ts";
import RidingLessonModel from "../models/ridingLessonModel.ts";
import {RidingLessonSchema} from "../types/ridingLesson.ts";
import UserModel from "../models/userModel.ts";
import {addDays, formatDate, getCurrentDate} from "../util/dateUtil.ts";
import {findHorse, findHorseById} from "./horseService.ts";
import invalidIdException from "../exceptions/invalidIdException.ts";

// deno-lint-ignore no-unused-vars
export async function findRidingLesson(trainer?: string, horses?: string[], fromDate?: string, toDate?: string, getPossibleRidingLessonCombinations?: boolean, bookedLessons?: boolean) {
    bookedLessons = getPossibleRidingLessonCombinations ? false : bookedLessons;

    let ridingLessons: RidingLessonModel[] | undefined;

    // Define the date range
    if (!fromDate && !toDate) {
        fromDate = getCurrentDate();
        toDate = addDays(7, fromDate);
    } else if (!fromDate) {
        fromDate = addDays(-7, toDate);
    } if (!toDate) { /* No else if because if it is an else if deno thinks that toDate could be undefined */
        toDate = addDays(7, fromDate);
    }

    // console.log(`fromDate: ${fromDate} - toDate: ${toDate}`);

    // Find the riding lessons with the given parameters
    if (trainer && horses && bookedLessons) {
        ridingLessons = await ridingLessonRepo.findBookedRidingLessonsByTrainerIdAndHorseIdsAndDay(trainer, horses, fromDate, toDate);
    } else if (horses && bookedLessons) {
        ridingLessons = await ridingLessonRepo.findBookedRidingLessonsByHorseIdAndDay(horses, fromDate, toDate);
    } else if (trainer && bookedLessons) {
        ridingLessons = await ridingLessonRepo.findBookedRidingLessonsByTrainerIdAndDay(trainer, fromDate, toDate);
    } else if (trainer) {
        ridingLessons = await ridingLessonRepo.findUnbookedRidingLessonByTrainerAndDay(trainer, fromDate, toDate);
    } else if (bookedLessons !== undefined) {
        if (bookedLessons) {
            ridingLessons = await ridingLessonRepo.findBookedRidingLessonsByDay(fromDate, toDate);
        } else {
            ridingLessons = await ridingLessonRepo.findUnbookedRidingLessonByDay(fromDate, toDate);
        }
    }
    else {
        ridingLessons = await ridingLessonRepo.findRidingLessonByDay(fromDate, toDate);
    }

    const ridingLessonsSchema: RidingLessonSchema[] = [];

    // Add possible riding lesson combinations to the result
    if (getPossibleRidingLessonCombinations) {
        horses = horses ? horses.map(horseIdToName) : (await findHorse()).map(horse => horse.name);

        for (const ridingLesson of ridingLessons) {
            for (const horse of horses) {
                ridingLessonsSchema.push({
                    _id: ridingLesson._id,
                    trainer: ridingLesson.trainer.name,
                    booked: ridingLesson.booked,
                    arena: ridingLesson.arena,
                    day: ridingLesson.day,
                    startHour: ridingLesson.startHour,
                    horse: horse,
                });
            }
        }
    } else {
        for (const ridingLesson of ridingLessons) {
            ridingLessonsSchema.push(ridingLessonModelToRidingLesson(ridingLesson));
        }
    }

    return ridingLessonsSchema;
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
    const ridingLessonSchema: RidingLessonSchema = {
        _id: ridingLesson._id,
        trainer: ridingLesson.trainer.name,
        booked: ridingLesson.booked,
        arena: ridingLesson.arena,
        day: ridingLesson.day,
        startHour: ridingLesson.startHour,
    }

    if (ridingLesson.horse) {
        ridingLessonSchema.horse = ridingLesson.horse.name;
    }

    return ridingLessonSchema;
}

async function horseIdToName(horseId: string) {
    const horse = await findHorseById(horseId);

    if (!horse) {
        throw new invalidIdException();
    }

    return horse.name;
}
