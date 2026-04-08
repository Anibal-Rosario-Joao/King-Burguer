package com.anibal.kingburguer.compose.signup

import TextString

data class FieldState(
    val field: String = "",
    val error: TextString? = null
)
data class FormState(
    val name: FieldState = FieldState(),
    val email: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val confirmPassword: FieldState = FieldState(),
    val document: FieldState = FieldState(),
    val birthday: FieldState = FieldState(),
    val formIsValid: Boolean = false
)
