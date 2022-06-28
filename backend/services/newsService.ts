import {NewsSchema} from "../types/news.ts";
import * as newsRepo from "../repositories/newsRepo.ts";
import NewsModel from "../models/newsModel.ts";
import UserModel from "../models/userModel.ts";

export async function findNews(user: UserModel): NewsSchema {
    const addresses: string[] = ["all", user.email, user.role];

    return (await newsRepo.findNewsByAddressees(addresses)).map(newsModelToNews);
}

export async function addNews(newsData: NewsSchema): NewsSchema {
    const id: string =  await newsRepo.createNews({
        caption: newsData.caption,
        text: newsData.text,
        addressees: newsData.addressees,
    });

    const newNews: NewsModel | undefined = await newsRepo.findNewsById(id);

    if (!newNews) {
        throw new Error("Error in addNews Method in newsService.");
    }

    return newsModelToNews(newNews);
}

/* ------------------------------ Util ------------------------------ */

function newsModelToNews(news: NewsModel): NewsSchema {
    return {
        _id: news._id,
        caption: news.caption,
        text: news.text,
        addressees: news.addressees,
    }
}
