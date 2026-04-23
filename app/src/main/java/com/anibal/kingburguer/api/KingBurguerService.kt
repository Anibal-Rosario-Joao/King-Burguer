package com.anibal.kingburguer.api

import com.anibal.kingburguer.data.UserRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface KingBurguerService {
    // @GET("kingburguer")
    // 1. suspend fun getTest(): Response<String>
    // 2. suspend fun getTest(): String com block try catch

    @POST("users")
    suspend fun postUser(@Body userRequest: UserRequest): String

    companion object{
        private const val  BASE_URL = "https://hades.tiagoaguiar.co/"

        fun create(): KingBurguerService{
            val logger = HttpLoggingInterceptor().apply {
                 level = HttpLoggingInterceptor.Level.BODY
            }

            val clientOk = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL) // ele vai adicional no final o Kingburguer
                .client(clientOk)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(KingBurguerService::class.java)
        }
    }
}