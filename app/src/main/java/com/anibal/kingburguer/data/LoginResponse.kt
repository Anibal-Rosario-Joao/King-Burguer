package com.anibal.kingburguer.data

import com.google.gson.annotations.SerializedName

data class LoginResponse(
        @SerializedName("access_token")
        val accessToken: String,
        @SerializedName("refresh_token")
        val refreshToken: String,
        @SerializedName("expires_seconds")
        val expiresSeconds: Double,
        @SerializedName("token_type")
        val tokenType: String
    )
//    data class Error(val detail: String): LoginResponse()
//    data class ErrorAuth(val detail: ErrorDetail): LoginResponse()


//data class ErrorDetail(val message: String) -> deixa