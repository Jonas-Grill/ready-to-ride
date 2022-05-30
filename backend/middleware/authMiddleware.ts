import {Context, Status, verify} from "../deps.ts";
import * as userService from "../services/userService.ts";
import {KEY} from "../config/config.ts";

const authMiddleware = async (ctx: Context, next: () => void) => {
    const authHeader = ctx.request.headers.get("authorization");

    ctx.assert(!(authHeader === null), Status.Unauthorized, "Please authenticate yourself");

    const jwt: string[] = authHeader.split(" ");

    ctx.assert(jwt[0] === "Bearer", Status.Unauthorized, "Wrong authorization method")

    try {
        // Validate JWT
        const email: string | unknown = await verify(jwt[1], KEY).then(payload => payload.email);

        ctx.assert(typeof email === "string", Status.Unauthorized, "JWT token is invalid");

        // If it is valid select user and save in context state
        ctx.state.currentUser = await userService.findUserByEmail(email);
    } catch (err) {
        if (err instanceof RangeError) {
            ctx.state.currentUser = null;

            ctx.throw(Status.Unauthorized, "JWT token is expired");
        }

        ctx.state.currentUser = null;
        ctx.throw(Status.Unauthorized, "JWT token is invalid");
    }

    await next();
};

export default authMiddleware;