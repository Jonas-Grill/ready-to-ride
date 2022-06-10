package com.readytoride.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://ready-to-ride-backend.tk/"

private val retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BASE_URL).build()

interface ApiService {
    @GET("horses")
    fun getHorses(): String
}

object rtrApi {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java) }
}