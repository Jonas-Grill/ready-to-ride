import {Context, isHttpError, Status} from "../deps.ts";

const errorHandlerMiddleware = async (ctx: Context, next: Function) => {
    try {
        await next();
    } catch (err) {
        console.log(err.stack);

        if (isHttpError(err)) {
            ctx.response.status = err.status;
            ctx.response.body = {msg: err.message};

            switch (err.status) {
                case Status.NotFound:
                    // handle NotFound
                    break;
                default:
                // handle other statuses
            }
        } else {
            // rethrow if you can't handle the error
            throw err;
        }

    }
}

export default errorHandlerMiddleware;