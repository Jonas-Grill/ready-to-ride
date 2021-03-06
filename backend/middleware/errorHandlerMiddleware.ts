import {Context, isHttpError, Status} from "../deps.ts";
import invalidDataException from "../exceptions/invalidDataException.ts";
import invalidIdException from "../exceptions/invalidIdException.ts";

const errorHandlerMiddleware = async (ctx: Context, next: () => void) => {
    try {
        await next();
    } catch (err) {
        console.log(err.stack);
        console.log(err.message)

        if (isHttpError(err)) {
            switch (err.status) {
                default:
                    ctx.response.status = err.status;
                    ctx.response.body = {msg: err.message};

            }
        } else if (err instanceof invalidIdException) {
            ctx.response.status = Status.BadRequest;
            ctx.response.body = {msg: "Please provide a valid id"};
        } else if (err instanceof invalidDataException) {
            ctx.response.status = Status.BadRequest;
            ctx.response.body = {msg: err.message};
        } else {
            ctx.response.status = Status.InternalServerError;
            ctx.response.body = {msg: "Internal server error"};
        }

    }
}

export default errorHandlerMiddleware;