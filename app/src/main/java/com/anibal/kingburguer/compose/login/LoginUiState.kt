package com.anibal.kingburguer.compose.login

import com.anibal.kingburguer.validation.TextString

data class LoginUiState(
    val isLoading: Boolean = false,
    val goToHome: Boolean = false,
    val error: TextString? = null
)
