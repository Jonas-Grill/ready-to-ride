import {Database, MongoClient} from "../deps.ts";
import {DB_LINK} from "./config.ts"

let db: Database;

export const getDb = async () => {
    if (db) {
        return db;
    } else {
        const client = new MongoClient();

        try {
            await client.connect(DB_LINK);
            db = client.database("ready-to-ride");

            return db;
        } catch (_e) {
            await client.connect("mongodb://localhost:27017");
            db = client.database("ready-to-ride");

            return db;
        }
    }
}

