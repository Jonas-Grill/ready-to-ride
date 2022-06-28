import StableModel from "../models/stableModel.ts";
import * as stableRepo from "../repositories/stableRepo.ts";
import {ExtendedStableSchema} from "../types/stable.ts";
import InvalidDataException from "../exceptions/invalidDataException.ts";

export const findStable = async () => {
    const stable: StableModel | undefined = (await stableRepo.findStable()).pop();

    if (!stable) {
        await initializeStable()
        throw new Error("Error in findStable Method in stableService.");
    }

    return stableModelToStable(stable);
}

export const initializeStable = async () => {
    if ((await stableRepo.findStable()).pop()) return;

    const stable: StableModel = {
        name: "Ready-to-Ride",
        description: "Eins toller Stall das, also gib uns dein Geld.",
        arenas: [
            {
                name: "Halle 1",
                size: 3200,
            },
            {
                name: "Halle 2",
                size: 3400,
            },
            {
                name: "Halle 3",
                size: 3000,
            }
        ],
        boxes: [
            {
                name: "Deluxe",
                price: 400,
                size: 36,
                count: 5,
            },
            {
                name: "NormalPlus",
                price: 300,
                size: 30,
                count: 6,
            },
            {
                name: "Normal",
                price: 250,
                size: 26,
                count: 8,
            }
        ],
        adminPasscode: "readyStall11",
        trainerPasscode: "readyTrainer33",
    }

    const id: string = await stableRepo.createStable(stable);

    const newStable: StableModel | undefined = await stableRepo.findStableById(id)

    if (!newStable) {
        throw new Error("Error in initializeStable Method in stableService.");
    }
}

// deno-lint-ignore no-explicit-any
export const updateStable = async (stable: any): Promise<ExtendedStableSchema> => {
    const oldStable: StableModel | undefined = (await stableRepo.findStable()).pop();

    if (!oldStable) {
        throw new Error("Error in updateStable Method in stableService.");
    }

    let newStable: StableModel | undefined = {
        _id: oldStable._id,
        name: stable.name || oldStable.name,
        description: stable.description || oldStable.description,
        arenas: stable.arenas || oldStable.arenas,
        boxes: stable.boxes || oldStable.boxes,
        adminPasscode: stable.adminPasscode || oldStable.adminPasscode,
        trainerPasscode: stable.trainerPasscode || oldStable.trainerPasscode,
    }

    if (doesStableHaveDuplicateArenaNames(stable)) {
        return Promise.reject(new InvalidDataException("Duplicate arena names are not allowed."));
    }

    newStable = await stableRepo.updateStable(newStable);

    if (!newStable) {
        throw new Error("Error in updateStable Method in stableService.");
    }

    return stableModelToStable(newStable);
}

/* ------------------------------ Util ------------------------------ */

const stableModelToStable = (stable: StableModel): ExtendedStableSchema => {
    return {
        name: stable.name,
        description: stable.description,
        arenas: stable.arenas,
        boxes: stable.boxes,
        adminPasscode: stable.adminPasscode,
        trainerPasscode: stable.trainerPasscode,
    };
}

export const doesArenaExist = async (name: string) => {
    const stable: StableModel = await findStable()

    return stable.arenas.some(arena => arena.name === name);
}

export const doesStableHaveDuplicateArenaNames = (stable: ExtendedStableSchema) => {
    const stableArenas: string[] = stable.arenas.map(arena => arena.name);

    return stableArenas.some((arena, index) => stableArenas.indexOf(arena) !== index);
}