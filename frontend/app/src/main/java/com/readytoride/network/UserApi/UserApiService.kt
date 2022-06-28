package com.readytoride.network.UserApi

import com.readytoride.network.HorseApi.HorseEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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

    @GET("users/me")
    suspend fun getMyUser(@Header("Authorization") token: String): UserEntity

    @PUT("users")
    suspend fun updateUser(@Header("Authorization") token: String, @Body requestBody: UserEntity): UserEntity

    @POST("users")
    suspend fun postNewUser(@Header("Authorization") token: String, @Body requestBody: UserEntity): HorseEntity

    @POST("users/login")
    suspend  fun login(@Body requestBody: LoginEntity): TokenEntity

}

object UserApi {
    val retrofitService : UserApiService by lazy {
        retrofit.create(UserApiService::class.java) }
}