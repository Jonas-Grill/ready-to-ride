import InvalidIdException from "../exceptions/invalidIdException.ts";

const imageDir = "./images/";

export async function findImage(id: string): Promise<Uint8Array> {
    try {
        return await Deno.readFile(`${imageDir}${id}.png`);
    } catch (_e) {
        throw new InvalidIdException()
    }
}

export async function addImage(data: Uint8Array): Promise<string> {
    const id: string = crypto.randomUUID();

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