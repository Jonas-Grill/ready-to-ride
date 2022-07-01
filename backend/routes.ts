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
    addHorse, addImageToHorse,
    deleteHorse,
    findHorse,
    findHorseById, findHorseColours, findHorseLevels,
    findHorseRaces, removeImageFromHorse,
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
import {download, upload} from "./controllers/imageController.ts";

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
    .get("/users/:id", findUserById)
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
    .get("/images/:id", download)
    // Auth required
    .use(authMiddleware)
    // User
    .get("/me/calendar", findRidingLessonsByCurrentUser)
    .get("/me", findCurrentUser)
    .get("/users/:id/calendar", findRidingLessonsByUserId)
    .put("/users", updateUser)
    // Horse
    .post("/horses", addHorse)
    .put("/horses/:id", updateHorse)
    .delete("/horses/:id", deleteHorse)
    .post("/horses/:id/images", addImageToHorse)
    .delete("/horses/:id/images/:imageId", removeImageFromHorse)
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
    // Image
    .post("/images", upload)
;

router.routes();

export default router;