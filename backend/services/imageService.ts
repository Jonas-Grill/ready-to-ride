import InvalidIdException from "../exceptions/invalidIdException.ts";

const imageDir = "./images/";

async function addImage(data: Uint8Array): Promise<string> {
    const id: string = crypto.randomUUID();

    await Deno.writeFile(`${imageDir}${id}.png`, data);

    return id;
}

async function findImage(id: string): Promise<Uint8Array> {
    const image = await Deno.readFile(`${imageDir}${id}.png`);

    if (!image) {
        throw new InvalidIdException()
    }

    return image;
}