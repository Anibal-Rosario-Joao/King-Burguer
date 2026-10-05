package com.anibal.kingburguer.data

data class UserCredencials(
    val accessToken: String = "",
    val refreshToken: String = "",
    val expiresTimestamp: Long = 0,
    val tokenTypes: String = ""
)