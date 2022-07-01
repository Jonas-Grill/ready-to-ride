package com.readytoride.network.ImageApi

import com.readytoride.network.NewsApi.NewsApiService
import com.readytoride.network.NewsApi.NewsEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "https://ready-to-ride-backend.tk/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

interface ImageApiService {

    @GET("images/{id}")
    suspend fun getImage(@Path("id") imageId: String): String

    @POST("images")
    suspend fun postNews(@Header("Authorization") token: String, @Body requestBody: ImageEntity): ImageEntity
}

object ImageApi {
    val retrofitService : ImageApiService by lazy {
        com.readytoride.network.ImageApi.retrofit.create(ImageApiService::class.java) }
}