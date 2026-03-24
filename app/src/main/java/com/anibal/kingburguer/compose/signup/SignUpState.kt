package com.anibal.kingburguer.compose.signup

data class SignUpState(
    val isLoading: Boolean = false,
    val goToHome: Boolean = false,
    val error: String? = null
)
