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
import {
    addHorse,
    deleteHorse,
    findHorse,
    findHorseById, findHorseColours, findHorseLevels,
    findHorseRaces,
    updateHorse
} from "./controllers/horseController.ts";

const router = new Router();

router
    .use(errorHandlerMiddleware)
    .get("/", (ctx) => {
        ctx.response.body = "Welcome";
    })
    // User
    .get("/users", findTrainer)
    .get("/users/roles", findUserRoles)
    .get("/users/focuses", findUserFocuses)
    .get("/users/proficiencies", findUserProficiencies)
    .post("/users", registration)
    .post("/users/login", login)
    // Horse
    .get("/horses", findHorse)
    .get("/horses/races", findHorseRaces)
    .get("/horses/colours", findHorseColours)
    .get("/horses/levels", findHorseLevels)
    .get("/horses/:id", findHorseById)
    // Auth required
    .use(authMiddleware)
    // User
    .get("/users/me", findCurrentUser)
    .get("/users/:id", findUserById)
    .put("/users", updateUser)
    // Horse
    .post("/horses", addHorse)
    .put("/horses/:id", updateHorse)
    .delete("/horses/:id", deleteHorse)

router.routes();

export default router;