import {Context, Status} from "../deps.ts";
import {UserRole} from "../types/userRole.ts";
import * as newsService from "../services/newsService.ts";
import {instanceOfNews, NewsSchema} from "../types/news.ts";

export const findNews = async (ctx: Context) => {
    ctx.response.status = Status.OK;
    ctx.response.body = (await newsService.findNews()).map((news) => {
        return {
            _id: news._id,
            caption: news.caption,
            text: news.text,
        }
    });
}


export const addNews = async (ctx: Context) => {
    ctx.assert(ctx.state.currentUser.role === UserRole.ADMIN || ctx.state.currentUser.role === UserRole.TRAINER, Status.Unauthorized, "Your role isn't authorized to access this function")

    ctx.assert(ctx.request.hasBody, Status.BadRequest, "Please provide data");

    const newsData = await ctx.request.body().value;

    ctx.assert(newsData, Status.BadRequest, "Please provide data");
    ctx.assert(instanceOfNews(newsData), Status.BadRequest, "Please provide valid data");

    const news: NewsSchema = await newsService.addNews(newsData);

    ctx.response.status = Status.Created;
    ctx.response.body = {
        _id: news._id,
        caption: news.caption,
        text: news.text,
    };
}