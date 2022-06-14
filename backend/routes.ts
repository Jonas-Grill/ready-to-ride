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
import {preAuthMiddleware, authMiddleware} from "./middleware/authMiddleware.ts";
import {
    addHorse,
    deleteHorse,
    findHorse,
    findHorseById, findHorseColours, findHorseLevels,
    findHorseRaces,
    updateHorse
} from "./controllers/horseController.ts";
import {findStable, updateStable} from "./controllers/stableController.ts";

const router = new Router();

router
    .use(errorHandlerMiddleware)
    .use(preAuthMiddleware)
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
    // Stable
    .get("/stable", findStable)
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
    // Stable
    .put("/stable", updateStable)

router.routes();

export default router;