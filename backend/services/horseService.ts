import * as horseRepo from "../repositories/horseRepo.ts";
import {ExtendedHorseSchema, HorseSchema} from "../types/horse.ts";
import HorseModel from "../models/horseModel.ts";
import {deleteImage} from "./imageService.ts";

export async function deleteHorse(id: string) {
    await horseRepo.deleteHorse(id);
}

export const findHorse = async () => {
    return (await horseRepo.findHorse()).map(horseModelToExtendedHorse);
}

export const findHorseById = async (id: string) => {
    const horse: HorseModel | undefined = await horseRepo.findHorseById(id)

    if (!horse) {
        return;
    }

    return horseModelToExtendedHorse(horse);
}

export const addHorse = async (horse: HorseSchema) => {
    const id: string = await horseRepo.createHorse(horse);

    const newHorse: HorseModel | undefined = await horseRepo.findHorseById(id)

    if (!newHorse) {
        throw new Error("Error in addHorse Method in horseService.");
    }

    return horseModelToExtendedHorse(newHorse);
}

// deno-lint-ignore no-explicit-any
export const updateHorse = async (horse: any, id: string) => {
    const oldHorse: HorseModel | undefined = await horseRepo.findHorseById(id);

    if (!(oldHorse)) {
        return;
    }

    const newHorse: HorseModel | undefined = await horseRepo.updateHorse({
        _id: oldHorse._id,
        name: horse.name || oldHorse.name,
        height: horse.height || oldHorse.height,
        race: horse.race || oldHorse.race,
        age: horse.age || oldHorse.age,
        colour: horse.colour || oldHorse.colour,
        difficultyLevel: horse.difficultyLevel || oldHorse.difficultyLevel,
        profilePicture: horse.profilePicture || oldHorse.profilePicture,
        description: horse.description || oldHorse.description,
        pictures: horse.pictures || oldHorse.pictures,
    });

    if (!newHorse) {
        throw new Error("Error in updateHorse Method in horseService.");
    }

    return horseModelToExtendedHorse(newHorse);
}

export const addImage = async (id: string, imageId: string) => {
    const horse: HorseModel | undefined = await horseRepo.findHorseById(id);

    if (!(horse)) {
        return;
    }

    if (horse.pictures) {
        horse.pictures.push(imageId);
    } else {
        horse.pictures = [imageId];
    }

    const newHorse: HorseModel | undefined = await horseRepo.updateHorse(horse);

    if (!newHorse) {
        throw new Error("Error in updateHorse Method in horseService.");
    }

    return horseModelToExtendedHorse(newHorse);
}

export const removeImage = async (id: string, imageId: string) => {
    await deleteImage(imageId)

    const horse: HorseModel | undefined = await horseRepo.findHorseById(id);

    if (!(horse)) {
        return;
    }

    if (horse.pictures) {
        const index = horse.pictures.indexOf(imageId, 0);
        if (index > -1) {
            horse.pictures.splice(index, 1);
        }
    }

    const newHorse: HorseModel | undefined = await horseRepo.updateHorse(horse);

    if (!newHorse) {
        throw new Error("Error in updateHorse Method in horseService.");
    }

    return horseModelToExtendedHorse(newHorse);
}

/* ------------------------------ Util ------------------------------ */

const horseModelToExtendedHorse = (horse: HorseModel): ExtendedHorseSchema => {
    return {
        _id: horse._id,
        name: horse.name,
        height: horse.height,
        race: horse.race,
        age: horse.age,
        colour: horse.colour,
        difficultyLevel: horse.difficultyLevel,
        profilePicture: horse.profilePicture,
        description: horse.description || "So much empty",
        pictures: horse.pictures || ["So much empty"]
    };
}

