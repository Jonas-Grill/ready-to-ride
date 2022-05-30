import {Router} from "./deps.ts";
import errorHandlerMiddleware from "./middleware/errorHandlerMiddleware.ts";
import {
    findCurrentUser,
    findTrainer,
    findUserById, findUserFocuses, findUserProficiencies, findUserRoles,
    login,
    registration,
    updateUser
} from "./controllers/userController.ts";
import authMiddleware from "./middleware/authMiddleware.ts";

const router = new Router();

router
    .use(errorHandlerMiddleware)
    .get("/", (ctx) => {
        ctx.response.body = "Welcome";
    })
    .get("/users", findTrainer)
    .get("/users/roles", findUserRoles)
    .get("/users/focuses", findUserFocuses)
    .get("/users/proficiencies", findUserProficiencies)
    .post("/users", registration)
    .post("/users/login", login)
    .use(authMiddleware)
    .get("/users/me", findCurrentUser)
    .get("/users/:id", findUserById)
    .put("/users", updateUser)

router.routes();

export default router;