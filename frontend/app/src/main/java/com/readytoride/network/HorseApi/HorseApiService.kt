package com.readytoride.network.HorseApi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "https://ready-to-ride-backend.tk/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

interface HorseApiService {

    @GET("horses")
    suspend fun getHorses(): List<HorseEntity>

    @GET("horses/{id}")
    suspend fun getHorseById(@Path("id") horseId: String): HorseEntity

    @GET("horses/races")
    suspend fun getHorseRaces(): List<String>

    @GET("horses/colours")
    suspend fun getHorseColours(): List<String>

    @GET("horses/levels")
    suspend fun getHorseLevels(): List<String>

    @POST("horses")
    suspend fun postNewHorse(@Header("Authorization") token: String, @Body requestBody: HorseEntity): HorseEntity

    @PUT("horses/{id}")
    suspend fun updateHorse(@Header("Authorization") token: String, @Path("id") horseId: String): HorseEntity

    @DELETE("horses/{id}")
    suspend fun deleteHorse(@Header("Authorization") token: String, @Path("id") horseId: String): String

    @DELETE("horses/{id}/images/{imageId}")
    suspend fun deleteHorsePicture(@Header("Authorization") token: String, @Path("id") horseId: String, @Path("imageId") imageId: String)
}

object HorseApi {
    val retrofitService : HorseApiService by lazy {
        retrofit.create(HorseApiService::class.java) }
}