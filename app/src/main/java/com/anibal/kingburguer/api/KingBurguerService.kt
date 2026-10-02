package com.anibal.kingburguer.api

import com.anibal.kingburguer.BuildConfig
import com.anibal.kingburguer.data.LoginRequest
import com.anibal.kingburguer.data.LoginResponse
import com.anibal.kingburguer.data.RefreshTokenRequest
import com.anibal.kingburguer.data.UserCreateResponse
import com.anibal.kingburguer.data.UserRequest
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface KingBurguerService {
    // @GET("kingburguer")
    // 1. suspend fun getTest(): Response<String>
    // 2. suspend fun getTest(): String com block try catch

    @POST("users")
    suspend fun postUser(
        @Body userRequest: UserRequest,
        @Header ("x-secret-key") secretKey: String = BuildConfig.X_SECRET_KEY
    ): Response<UserCreateResponse>

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
        @Header("x-secret-key") secretKey: String = BuildConfig.X_SECRET_KEY
    ): Response<LoginResponse>

    @PUT("auth/refresh-token")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest,
        @Header("Authorization") token: String
    ): Response<LoginResponse>

    companion object{
        private const val  BASE_URL = "https://hades.tiagoaguiar.co/kingburguer/"

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