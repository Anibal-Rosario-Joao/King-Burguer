package com.anibal.kingburguer.data

sealed class UserCreateResponse{
    data class Sucess(
        val id: Int,
        val name: String,
        val email: String,
        val document: String,
        val birthday: String
    ): UserCreateResponse()
    data class Error(val detail: String): UserCreateResponse()
    data class ErrorAuth(val detail: ErrorDetail): UserCreateResponse()
}

data class ErrorDetail(val message: String)