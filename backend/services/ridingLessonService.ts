import * as ridingLessonRepo from "../repositories/ridingLessonRepo.ts";
import RidingLessonModel from "../models/ridingLessonModel.ts";
import {RidingLessonSchema} from "../types/ridingLesson.ts";
import UserModel from "../models/userModel.ts";
import {addDays, getCurrentDate} from "../util/dateUtil.ts";
import {findHorse, findHorseById} from "./horseService.ts";
import invalidIdException from "../exceptions/invalidIdException.ts";

const ridingLessonLength = 1;

export const findRidingLesson = async (trainer?: string, horses?: string[], fromDate?: string, toDate?: string, getPossibleRidingLessonCombinations?: boolean, bookedLessons?: boolean): Promise<RidingLessonSchema[]> => {
    bookedLessons = getPossibleRidingLessonCombinations ? false : bookedLessons;

    let ridingLessons: RidingLessonModel[] | undefined;

    // Define the date range
    if (!fromDate && !toDate) {
        fromDate = getCurrentDate();
        toDate = addDays(7, fromDate);
    } else if (!fromDate) {
        fromDate = addDays(-7, toDate);
    }
    if (!toDate) { /* No else if because if it is an else if deno thinks that toDate could be undefined */
        toDate = addDays(7, fromDate);
    }

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
    } else {
        ridingLessons = await ridingLessonRepo.findRidingLessonByDay(fromDate, toDate);
    }

    const ridingLessonsSchema: RidingLessonSchema[] = [];

    // Add possible riding lesson combinations to the result
    if (getPossibleRidingLessonCombinations) {
        let possibleHorses: { name: string, id: string }[];

        if (horses) {
            possibleHorses = await Promise.all(horses.map(async (horseId) => {
                const horse = await findHorseById(horseId);
                if (!horse) {
                    throw new invalidIdException();
                } else {
                    return {
                        name: horse.name,
                        id: horse._id?.toString() || "",
                    };
                }
            }));
        } else {
            possibleHorses = (await findHorse()).map(horse => {
                return {
                    name: horse.name,
                    id: horse._id?.toString() || "",
                };
            });
        }

        for (const ridingLesson of ridingLessons) {
            for (const horse of possibleHorses) {
                if (await horseFreeAtTime(horse.id, ridingLesson.day, ridingLesson.startHour)) {
                    ridingLessonsSchema.push({
                        _id: ridingLesson._id,
                        trainer: ridingLesson.trainer.name,
                        booked: ridingLesson.booked,
                        arena: ridingLesson.arena,
                        day: ridingLesson.day,
                        startHour: ridingLesson.startHour,
                        horse: horse.name,
                    });
                }
            }
        }
    } else {
        for (const ridingLesson of ridingLessons) {
            ridingLessonsSchema.push(ridingLessonModelToRidingLesson(ridingLesson));
        }
    }

    return ridingLessonsSchema;
}

export const addRidingLesson = async (ridingLesson: RidingLessonSchema, currentUser: UserModel): Promise<RidingLessonSchema> => {
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

const horseFreeAtTime = async (horse: string, day: string, startHour: number): Promise<boolean> => {
    const ridingLessons = await ridingLessonRepo.findBookedRidingLessonsByDayAndHorseIdAndStartHour(day, horse, startHour);
    return ridingLessons.length === 0;
}
