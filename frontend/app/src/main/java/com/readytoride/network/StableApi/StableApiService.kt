package com.readytoride.network.StableApi

import com.readytoride.network.LessonApi.LessonEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "https://ready-to-ride-backend.tk/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

interface StableApiService {

    @GET("stable")
    suspend fun getStables(): StableEntity

    @GET("stable/arenas/{name}/calendar")
    suspend fun getBookingForArena(@Header("Authorization") token: String, @Path("name") arenaName: String): List<LessonEntity>

    @PUT("stable")
    suspend fun updateStable(@Header("Authorization") token: String, @Body requestBody: StableEntity): StableEntity
}

object StableApi {
    val retrofitService : StableApiService by lazy {
        retrofit.create(StableApiService::class.java) }
}