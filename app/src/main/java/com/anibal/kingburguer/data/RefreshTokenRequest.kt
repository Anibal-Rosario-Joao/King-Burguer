package com.anibal.kingburguer.data

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest (
    @SerializedName("refresh_token")
    val refreshToken: String
)