import "https://deno.land/x/dotenv@v3.2.0/load.ts";
import {config} from "https://deno.land/x/dotenv@v3.2.0/mod.ts";
import {Algorithm} from "../deps.ts";

console.log(config({ safe: true }));

const instanceOfAlgorithm = (algorithm: string): algorithm is Algorithm => {
    const supportedAlgorithms = ["HS256", "HS384", "HS512", "PS256", "PS384", "PS512", "RS256", "RS384", "RS512"];

    return supportedAlgorithms.includes(algorithm);
}

const PORT = parseInt(Deno.env.get('SERVER_PORT') || "8008");
const ENV = Deno.env.get('ENVIRONMENT') || "dev";
const APK_FILE_PATH = Deno.env.get('APK_FILE_PATH') || "";
const DB_LINK = Deno.env.get('DB_LINK') || "";

// const CERT_PATH = Deno.env.get('CERT_PATH') || "";
// const KEY_PATH = Deno.env.get('KEY_PATH');
const CERT_PATH = "./fullchain.pem";
const KEY_PATH = "./privkey.pem"

if (ENV === "prod") {
    const PRIVKEY_PEM = Deno.env.get('PRIVKEY_PEM') || "nope";
    const FULLCHAIN_PEM = Deno.env.get('FULLCHAIN_PEM') || "nope";

    await Deno.writeTextFile(KEY_PATH, PRIVKEY_PEM);
    await Deno.writeTextFile(CERT_PATH, FULLCHAIN_PEM);
}

const tmp = Deno.env.get('SIGN_ALG') || "RS256";

const SIGN_ALG: Algorithm = instanceOfAlgorithm(tmp) ? tmp : "HS256";
const KEY_GEN_ALG = Deno.env.get('KEY_GEN_ALG');
const KEY_GEN_CURVE = Deno.env.get('KEY_GEN_CURVE');

const KEY = (KEY_GEN_ALG && KEY_GEN_CURVE) ?
    await crypto.subtle.generateKey(
        {name: KEY_GEN_ALG, hash: KEY_GEN_CURVE},
        true,
        ["sign", "verify"],
    ) :
    await crypto.subtle.generateKey(
        {name: "HMAC", hash: "SHA-256"},
        true,
        ["sign", "verify"],
    )
;

export {
    PORT,
    ENV,
    APK_FILE_PATH,
    DB_LINK,
    CERT_PATH,
    KEY_PATH,
    SIGN_ALG,
    KEY,
};