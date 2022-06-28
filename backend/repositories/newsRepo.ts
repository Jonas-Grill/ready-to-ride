import {Bson} from "../deps.ts";
import db from "../config/db-connection.ts";
import News from "../models/newsModel.ts"
import InvalidIdException from "../exceptions/invalidIdException.ts";

const newsDb = db.collection<News>("news");

export const createNews = async (news: News) => {
    const id = await newsDb.insertOne({
        caption: news.caption,
        text: news.text,
        addressees: news.addressees,

    });

    return id.toString();
}

export const findNews = async () => {
    return await newsDb.find({}).toArray();
}

export const findNewsById = async (id: string) => {
    if (!Bson.ObjectId.isValid(id)) {
        throw new InvalidIdException();
    }

    return await newsDb.findOne({
        _id: new Bson.ObjectId(id),
    });
}

export const findNewsByAddressees = async (addresses: string[]) => {
    return await newsDb.find({
        addressees: {$in: addresses},
    }).toArray();
}

export const updateNews = async (news: News) => {
    if (!news._id) {
        throw new InvalidIdException();
    }

    if (!Bson.ObjectId.isValid(news._id.toString())) {
        throw new InvalidIdException();
    }

    await newsDb.updateOne(
        {
            _id: new Bson.ObjectId(news._id.toString()),
        },
        {
            $set: {
                caption: news.caption,
                text: news.text,
                addressees: news.addressees,
            }
        },
    );

    return await findNewsById(news._id.toString());
}

export const deleteNews = async (id: string) => {
    if (!Bson.ObjectId.isValid(id)) {
        throw new InvalidIdException();
    }

    return await newsDb.deleteOne({
        _id: new Bson.ObjectId(id),
    });
}