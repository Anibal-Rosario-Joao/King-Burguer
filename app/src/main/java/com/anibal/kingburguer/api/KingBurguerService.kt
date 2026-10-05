package com.anibal.kingburguer.api

import com.anibal.kingburguer.BuildConfig
import com.anibal.kingburguer.data.CouponBaseResponse
import com.anibal.kingburguer.data.CouponResponse
import com.anibal.kingburguer.data.FeedResponse
import com.anibal.kingburguer.data.HighlightProductResponse
import com.anibal.kingburguer.data.LoginRequest
import com.anibal.kingburguer.data.LoginResponse
import com.anibal.kingburguer.data.ProductDetailResponse
import com.anibal.kingburguer.data.ProfileResponse
import com.anibal.kingburguer.data.RefreshTokenRequest
import com.anibal.kingburguer.data.UserCreateResponse
import com.anibal.kingburguer.data.UserRequest
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    @POST("products/{id}/coupon")
    suspend fun createCoupon(
        @Header("Authorization") token: String,
        @Path("id") productId: Int
    ): Response<CouponResponse>

    @GET("feed")
    suspend fun fetchFeed(
        @Header("Authorization") token: String
    ): Response<FeedResponse>

    @PUT("auth/refresh-token")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest,
        @Header("Authorization") token: String
    ): Response<LoginResponse>

    @GET("products/{id}")
    suspend fun fetchProductById(
        @Header("Authorization") token: String,
        @Path("id") productId: Int
    ): Response<ProductDetailResponse>

    @GET("users/me")
    suspend fun fetchMe(
        @Header("Authorization") token: String,
    ): Response<ProfileResponse>

    @GET("coupons")
    suspend fun fetchCoupons(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 0,
        @Query("expired") expired: Boolean? = null
    ): Response<CouponBaseResponse> // Agora retorna o CouponBaseResponse

    @GET("highlight")
    suspend fun fetchHighlight(
        @Header("Authorization") token: String,
    ): Response<HighlightProductResponse>

    companion object{
        private const val  BASE_URL = "https://hades.tiagoaguiar.co/kingburguer/"

        fun create(): KingBurguerService{
            val logger = HttpLoggingInterceptor().apply {
                 level = HttpLoggingInterceptor.Level.BODY
            }

            val clientOk = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL) // ele vai adicional no final o Kingburguer
                .client(clientOk)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(KingBurguerService::class.java)
        }
    }
}