import InvalidIdException from "../exceptions/invalidIdException.ts";
import {ensureDir} from "../deps.ts";

export const imageDir = "./images/";

export async function findImage(id: string): Promise<Uint8Array> {
    id = id.replace(/\s/g, '');
    id = id.replace(/\/\.\.\//g, "");

    while (id.includes(".") || id.includes("/")) {
        id = id.replace("..", '');
        id = id.replace("/", '');
    }

    try {
        return await Deno.readFile(`${imageDir}${id}.png`);
    } catch (_e) {
        throw new InvalidIdException()
    }
}

export async function addImage(data: Uint8Array): Promise<string> {
    const id: string = crypto.randomUUID();

    ensureDir(imageDir);

    await Deno.writeFile(`${imageDir}${id}.png`, data);

    return id;
}

export async function deleteImage(id: string): Promise<void> {
    try {
        await Deno.remove(`${imageDir}${id}.png`);
    } catch (_e) {
        // Do nothing
    }
}