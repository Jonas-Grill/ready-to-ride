import {Context, helpers, Status} from "./../deps.ts";
import {ExtendedHorseSchema} from "../types/horse.ts";
import * as horseService from "../services/horseService.ts";

const imageDir = "./images/";

export const upload = async (ctx: Context) => {
    ctx.assert(ctx.request.hasBody, Status.BadRequest, "Please provide data");

    const data: Uint8Array = await ctx.request.body({type: 'bytes'}).value;
    ctx.assert(data, Status.BadRequest, "Please provide data");

    const id: string = crypto.randomUUID();

    await Deno.writeFile(`${imageDir}${id}.png`, data);

    ctx.response.status = Status.Created;
    ctx.response.body = {_id: id};
};

export const download = async (ctx: Context) => {
    const id: string = helpers.getQuery(ctx, { mergeParams: true }).id;

    ctx.assert(id, Status.BadRequest, "Please provide an id");

    const image = await Deno.readFile(`${imageDir}${id}.png`);

    ctx.assert(image, Status.NotFound, `Couldn't find an image with the id ${id}`);

    ctx.response.status = Status.OK;
    ctx.response.body = image;
    ctx.response.headers.set('Content-Type', 'image/png');
};