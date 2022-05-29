import {Router} from "./deps.ts";
import errorHandlerMiddleware from "./middleware/errorHandlerMiddleware.ts";
import {findTrainer, login, registration, updateUser} from "./controllers/userController.ts";
import authMiddleware from "./middleware/authMiddleware.ts";

const router = new Router();

router
    .use(errorHandlerMiddleware)
    .get("/", (ctx) => {
        ctx.response.body = "Welcome";
    })
    .get("/users", findTrainer)
    .post("/users", registration)
    .post("/users/login", login)
    .use(authMiddleware)
    .put("/users", updateUser)
;

router.routes();

export default router;