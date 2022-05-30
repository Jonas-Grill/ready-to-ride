import {MongoClient} from "../deps.ts";
import {DB_LINK} from "./config.ts"

const client = new MongoClient();

await client.connect(DB_LINK);

const db = client.database("ready-to-ride");

export default db;