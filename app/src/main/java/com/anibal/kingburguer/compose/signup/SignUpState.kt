package com.anibal.kingburguer.compose.signup

import TextString

data class SignUpState(
    val isLoading: Boolean = false,
    val goToLogin: Boolean = false,
    val error: TextString? = null
)
