import "https://deno.land/x/dotenv@v3.2.0/load.ts";
import {config} from "https://deno.land/x/dotenv@v3.2.0/mod.ts";
import {Algorithm} from "../deps.ts";

console.log(config({ safe: true }));

const instanceOfAlgorithm = (algorithm: string): algorithm is Algorithm => {
    const supportedAlgorithms = ["HS256", "HS384", "HS512", "PS256", "PS384", "PS512", "RS256", "RS384", "RS512"];

    return supportedAlgorithms.includes(algorithm);
}

const DENO_DIR = Deno.env.get('DENO_DIR') || "/packages";
const PORT = parseInt(Deno.env.get('SERVER_PORT') || "8008");
const ENV = Deno.env.get('ENVIRONMENT') || "dev";
const DB_LINK = Deno.env.get('DB_LINK') || "";

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
    DENO_DIR,
    PORT,
    KEY,
    SIGN_ALG,
    ENV,
    DB_LINK
};