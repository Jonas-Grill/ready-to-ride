import {Context} from "https://deno.land/x/oak@v10.6.0/context.ts";
import {Status} from "https://deno.land/std@0.140.0/http/http_status.ts";
import {ExtendedStableSchema, StableSchema} from "../types/stable.ts";
import * as stableService from "../services/stableService.ts";
import {UserRole} from "../types/userRole.ts";

export const findStable = async (ctx: Context) => {
    const stable: ExtendedStableSchema = await stableService.findStable();

    if (ctx.state.currentUser && ctx.state.currentUser.role === UserRole.ADMIN) {
        ctx.response.status = Status.OK;
        ctx.response.body = stable;
    } else {
        ctx.response.status = Status.OK;
        ctx.response.body = {
            name: stable.name,
            description: stable.description,
            arenas: stable.arenas,
            boxes: stable.boxes,
        };
    }
};

export const updateStable = async (ctx: Context) => {
    ctx.assert(ctx.state.currentUser.role === UserRole.ADMIN, Status.Unauthorized, "Your role isn't authorized to access this function")
    ctx.assert(ctx.request.hasBody, Status.BadRequest, "Please provide data");

    const stableData = await ctx.request.body().value;

    ctx.assert(stableData, Status.BadRequest, "Please provide data");

    const stable: StableSchema = await stableService.updateStable(stableData);

    ctx.response.status = Status.OK;
    ctx.response.body = stable;
};