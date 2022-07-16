package com.readytoride.network.UserApi

import com.google.gson.JsonObject
import com.readytoride.network.HorseApi.HorseEntity
import com.readytoride.network.LessonApi.LessonEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL =
    "https://ready-to-ride-backend.tk/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

interface UserApiService {

    @GET("users")
    suspend fun getUsers(): List<UserEntity>

    @GET("users/{id}")
    suspend fun getUserById(@Header("Authorization") token: String, @Path("id") userId: String): UserEntity

    @GET("users/{id}/calendar")
    suspend fun getUserCalendar(@Header("Authorization") token: String): CalendarEntity

    @GET("users/roles")
    suspend fun getRoles(@Path("id") userId: String): List<String>

    @GET("users/focuses")
    suspend fun getFocuses(): List<String>

    @GET("users/proficiencies")
    suspend fun getProficiencies(): List<String>

    @GET("me")
    suspend fun getMyUser(@Header("Authorization") token: String): UserEntity

    @GET("me/calendar")
    suspend fun getMyUserCalendar(@Header("Authorization") token: String): MutableList<LessonEntity>

    @PUT("users")
    suspend fun updateUser(@Header("Authorization") token: String, @Body requestBody: RequestBody): UserEntity

    @POST("users")
    suspend fun postNewUser(@Body requestBody: RequestBody): UserEntity

    @POST("users/login")
    suspend  fun login(@Body requestBody: LoginEntity): TokenEntity

}

object UserApi {
    val retrofitService : UserApiService by lazy {
        retrofit.create(UserApiService::class.java) }
}