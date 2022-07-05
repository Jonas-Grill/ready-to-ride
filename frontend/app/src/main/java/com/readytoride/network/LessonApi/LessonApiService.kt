package com.readytoride.network.LessonApi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "https://ready-to-ride-backend.tk/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

interface LessonApiService {

    @GET("ridinglessons")
    suspend fun getLessons(@Query("trainer") trainer: String,
                           @Query("horses") horses: MutableList<String>,
                           @Query("fromDate") fromDate: String,
                           @Query("toDate") toDate: String,
                           @Query("getPossibleHorseCombinations") getPossibleHorseCombinations: Boolean,
                           @Query("bookedLessons") bookedLessons: Boolean
    ): MutableList<LessonEntity>

    //probably not needed
    //@POST("ridinglessons")
    //suspend fun postNewLesson(@Header("Authorization") token: String, @Body requestBody: PostingLessonEntity): LessonEntity

    @POST("ridinglessons/multiple")
    suspend fun postNewLessons(@Header("Authorization") token: String, @Body requestBody: PostingLessonEntity): List<LessonEntity>

    @POST("ridinglessons/{id}/book")
    suspend fun makeBookingRequest(@Header("Authorization") token: String, @Body requestBody: HorseIdForLesson, @Path("id") lessonId: String): LessonEntity

    @DELETE("ridingkessons/{id}")
    suspend fun cancelLesson(@Header("Authorization") token: String, @Path("id") lessonId: String)
}

object LessonApi {
    val retrofitService : LessonApiService by lazy {
        retrofit.create(LessonApiService::class.java) }
}