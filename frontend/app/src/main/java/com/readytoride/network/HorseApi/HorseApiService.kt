package com.readytoride.network.HorseApi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://ready-to-ride-backend.tk/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

interface HorseApiService {

    @GET("horses")
    suspend fun getHorses(): List<HorseEntity>

    @GET("horses/races")
    suspend fun getHorseRaces(): List<String>

    @GET("horses/colours")
    suspend fun getHorseColours(): List<String>

    @GET("horses/levels")
    suspend fun getHorseLevels(): List<String>
}

object HorseApi {
    val retrofitService : HorseApiService by lazy {
        retrofit.create(HorseApiService::class.java) }
}