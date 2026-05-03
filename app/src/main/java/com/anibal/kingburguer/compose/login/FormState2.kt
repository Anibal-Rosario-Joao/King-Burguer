package com.anibal.kingburguer.compose.login

import TextString
import com.anibal.kingburguer.compose.signup.FieldState

data class FormState2(
    val email: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val rememberMe: Boolean = false,
    val formIsValid: Boolean = false
)
