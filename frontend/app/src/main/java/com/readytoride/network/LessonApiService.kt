package com.readytoride.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL =
    "https://ready-to-ride-backend.tk/"

private val retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BASE_URL).build()

interface LessonApiService {
}

object LessonApi {
    val retrofitService : LessonApiService by lazy {
        retrofit.create(LessonApiService::class.java) }
}