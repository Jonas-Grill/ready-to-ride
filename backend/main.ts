import {Application, logger, oakCors} from "./deps.ts";
import router from "./routes.ts";
import {CERT_PATH, ENV, KEY_PATH, PORT} from "./config/config.ts";

const app = new Application();

app.use(
    oakCors({
        origin: [
            "http://localhost:5000",
            "http://localhost:4200",
        ],
        credentials: true,
    })
);
app.use(logger.logger)
app.use(logger.responseTime)
app.use(router.routes());
app.use(router.allowedMethods());

app.addEventListener("listen", ({hostname, port, secure}) => {
    console.log(
        `Listening on: ${secure ? "https://" : "http://"}${hostname ??
        "localhost"}:${port}`,
    );
});

app.addEventListener("error", (evt) => {
    console.log(evt.error);
});

if (ENV === "dev") {
    await app.listen({
        port: PORT,
        secure: false,
    });
} else if (ENV === "prod") {
    await app.listen({
        port: PORT,
        secure: false,
        // certFile: CERT_PATH,
        // keyFile: KEY_PATH,
    });
}