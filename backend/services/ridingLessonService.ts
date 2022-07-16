import * as ridingLessonRepo from "../repositories/ridingLessonRepo.ts";
import RidingLessonModel from "../models/ridingLessonModel.ts";
import {CreateMultipleRidingLessonSchema, RidingLessonSchema} from "../types/ridingLesson.ts";
import UserModel from "../models/userModel.ts";
import {getDateRange} from "../util/dateUtil.ts";
import {findHorse, findHorseById} from "./horseService.ts";
import invalidIdException from "../exceptions/invalidIdException.ts";
import {doesArenaExist} from "./stableService.ts";
import invalidDataException from "../exceptions/invalidDataException.ts";
import {addNews} from "./newsService.ts";
import {findUserById} from "./userService.ts";

const ridingLessonDurationInHours = 1;

export const findRidingLesson = async (trainer?: string[], horses?: string[], fromDate?: string, toDate?: string, getPossibleRidingLessonCombinations?: boolean, bookedLessons?: boolean): Promise<RidingLessonSchema[]> => {
    bookedLessons = getPossibleRidingLessonCombinations ? false : bookedLessons;

    let ridingLessons: RidingLessonModel[] | undefined;

    const dateRange = getDateRange(fromDate, toDate)
    fromDate = dateRange.fromDate;
    toDate = dateRange.toDate;

    // Find the riding lessons with the given parameters
    if (trainer && horses && bookedLessons) {
        ridingLessons = await ridingLessonRepo.findBookedRidingLessonsByTrainerIdAndHorseIdsAndDay(trainer, horses, fromDate, toDate);
    } else if (horses && bookedLessons) {
        ridingLessons = await ridingLessonRepo.findBookedRidingLessonsByHorseIdAndDay(horses, fromDate, toDate);
    } else if (trainer && bookedLessons) {
        ridingLessons = await ridingLessonRepo.findBookedRidingLessonsByTrainerIdAndDay(trainer, fromDate, toDate);
    } else if (trainer && bookedLessons === false) {
        ridingLessons = await ridingLessonRepo.findUnbookedRidingLessonByTrainerAndDay(trainer, fromDate, toDate);
    } else if (trainer && bookedLessons === undefined) {
        ridingLessons = await ridingLessonRepo.findRidingLessonsByTrainerIdAndDay(trainer, fromDate, toDate);
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
                        horse: horse,
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

export async function findRidingLessonsByTrainerIdAndDay(id: string, fromDate: string, toDate: string) {
    const dateRange = getDateRange(fromDate, toDate)
    fromDate = dateRange.fromDate;
    toDate = dateRange.toDate;

    const ridingLessons = await ridingLessonRepo.findRidingLessonsByTrainerIdAndDay([id], fromDate, toDate);
    return ridingLessons.map(ridingLessonModelToRidingLesson);
}

export async function findRidingLessonsByArenaAndDay(name: string, fromDate: string, toDate: string) {
    const dateRange = getDateRange(fromDate, toDate)
    fromDate = dateRange.fromDate;
    toDate = dateRange.toDate;

    const ridingLessons: RidingLessonModel[] = await ridingLessonRepo.findRidingLessonsByArenaAndDay(name, fromDate, toDate);

    return ridingLessons.map(ridingLesson => {
        return ridingLessonModelToRidingLesson(ridingLesson);
    });
}

export async function findRidingLessonsByBookerEmailAndDay(email: string, fromDate: string, toDate: string) {
    const dateRange = getDateRange(fromDate, toDate)
    fromDate = dateRange.fromDate;
    toDate = dateRange.toDate;

    const ridingLessons: RidingLessonModel[] = await ridingLessonRepo.findRidingLessonsByBookerEmailAndDay(email, fromDate, toDate);

    return ridingLessons.map(ridingLesson => {
        return ridingLessonModelToRidingLesson(ridingLesson);
    });
}

export const addRidingLesson = async (ridingLesson: RidingLessonSchema, currentUser: UserModel): Promise<RidingLessonSchema> => {
    if (!await doesArenaExist(ridingLesson.arena)) {
        return Promise.reject(new invalidDataException("The arena does not exist"));
    } else if (!await arenaFreeAtTime(ridingLesson.arena, ridingLesson.day, ridingLesson.startHour)) {
        return Promise.reject(new invalidDataException("The arena is not free at the given time"));
    }

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

export async function addMultipleRidingLessons(ridingLessonsData: CreateMultipleRidingLessonSchema, currentUser: UserModel): Promise<RidingLessonSchema[]> {
    const ridingLessons: RidingLessonSchema[] = [];

    if (!await doesArenaExist(ridingLessonsData.arena)) {
        return Promise.reject(new invalidDataException("The arena does not exist"));
    }

    for (let i = ridingLessonsData.startHour; i < ridingLessonsData.endHour; i += ridingLessonDurationInHours) {
        if (!await arenaFreeAtTime(ridingLessonsData.arena, ridingLessonsData.day, i)) {
            return Promise.reject(new invalidDataException("The arena is not free at the given time"));
        }
    }

    for (let i = ridingLessonsData.startHour; i < ridingLessonsData.endHour; i += ridingLessonDurationInHours) {
        const ridingLesson: RidingLessonSchema = {
            arena: ridingLessonsData.arena,
            day: ridingLessonsData.day,
            startHour: i,
        }

        const newRidingLesson: RidingLessonSchema = await addRidingLesson(ridingLesson, currentUser);

        ridingLessons.push(newRidingLesson);
    }

    return ridingLessons;
}

export const bookRidingLesson = async (bookingData: { horseId: string, lessonId: string }, currentUser: UserModel): Promise<void> => {
    const horse = await findHorseById(bookingData.horseId);
    if (!horse) {
        throw new invalidIdException();
    }

    const lesson = await ridingLessonRepo.findRidingLessonById(bookingData.lessonId);
    if (!lesson) {
        throw new invalidIdException();
    }

    if (lesson.booked) {
        throw new invalidDataException("This lesson is already booked");
    }

    await ridingLessonRepo.updateRidingLesson({
        _id: lesson._id,
        arena: lesson.arena,
        bookerEmail: currentUser.email,
        day: lesson.day,
        startHour: lesson.startHour,
        trainer: lesson.trainer,
        booked: true,
        horse: {
            name: horse.name,
            id: horse._id?.toString() || ""
        }
    });

    addNews({
        caption: "Riding Lesson Booked",
        text: `${currentUser.name.firstName} ${currentUser.name.lastName} has booked a riding lesson for ${horse.name} on ${lesson.day} at ${lesson.startHour}`,
        addressees: (await findUserById(lesson.trainer.id))?.email || "",
    });
}

export async function cancelRidingLesson(id: string, currentUser: UserModel): Promise<void> {
    const lesson = await ridingLessonRepo.findRidingLessonById(id);

    if (!lesson) {
        throw new invalidIdException();
    }

    if (lesson.trainer.id !== currentUser._id?.toString()) {
        throw new invalidDataException("You are not the trainer of this lesson");
    }

    await ridingLessonRepo.deleteRidingLesson(id);

    if (lesson.booked && lesson.horse && lesson.bookerEmail) {
        addNews({
            caption: "Riding Lesson Cancelled",
            text: `${currentUser.name.firstName} ${currentUser.name.lastName} has cancelled a riding lesson for ${lesson.horse.name} on ${lesson.day} at ${lesson.startHour}`,
            addressees: lesson.bookerEmail,
        });
    }
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
        ridingLessonSchema.horse = ridingLesson.horse;
    }

    return ridingLessonSchema;
}

const horseFreeAtTime = async (horse: string, day: string, startHour: number): Promise<boolean> => {
    const ridingLessons = await ridingLessonRepo.findBookedRidingLessonsByDayAndHorseIdAndStartHour(day, horse, startHour);
    return ridingLessons.length === 0;
}

const arenaFreeAtTime = async (arena: string, day: string, startHour: number): Promise<boolean> => {
    const ridingLessons = await ridingLessonRepo.findRidingLessonsByDayAndArenaAndStartHour(day, arena, startHour);
    return ridingLessons.length === 0;
}
