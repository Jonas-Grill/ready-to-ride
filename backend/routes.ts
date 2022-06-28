import {Router, upload} from "./deps.ts";
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
import {
    addMultipleRidingLessons,
    addRidingLesson,
    bookRidingLesson, cancelRidingLesson,
    findRidingLesson, findRidingLessonsByArenaAndDay, findRidingLessonsByCurrentUser, findRidingLessonsByUserId
} from "./controllers/ridingLessonController.ts";
import {addNews, findNews} from "./controllers/newsController.ts";

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
    // Riding Lesson
    .get("/ridinglessons", findRidingLesson)
    // News
    .get("/news", findNews)
    // Image
    .post("/image", upload('uploads', { extensions: ['jpg', 'png'], maxSizeBytes: 20000000, maxFileSizeBytes: 10000000, saveFile: true }),
        async (context: any) => {
            context.response.body = context.uploadedFiles;
        }
    )
    // Auth required
    .use(authMiddleware)
    // User
    .get("/users/me/calendar", findRidingLessonsByCurrentUser)
    .get("/users/me", findCurrentUser)
    .get("/users/:id/calendar", findRidingLessonsByUserId)
    .get("/users/:id", findUserById)
    .put("/users", updateUser)
    // Horse
    .post("/horses", addHorse)
    .put("/horses/:id", updateHorse)
    .delete("/horses/:id", deleteHorse)
    // Stable
    .get("/stable/arenas/:name/calendar", findRidingLessonsByArenaAndDay)
    .put("/stable", updateStable)
    // Riding Lesson
    .post("/ridinglessons", addRidingLesson)
    .post("/ridinglessons/multiple", addMultipleRidingLessons)
    .post("/ridinglessons/:id/book", bookRidingLesson)
    .delete("/ridinglessons/:id", cancelRidingLesson)
    // News
    .post("/news", addNews)
;

router.routes();

export default router;