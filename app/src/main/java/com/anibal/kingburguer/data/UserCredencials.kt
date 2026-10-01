package com.anibal.kingburguer.data

data class UserCredencials(
    val accessToken: String,
    val refreshToken: String,
    val expiresTimestamp: Long,
    val tokenTypes: String
)