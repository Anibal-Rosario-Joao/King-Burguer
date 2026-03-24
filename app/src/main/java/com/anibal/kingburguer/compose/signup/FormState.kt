package com.anibal.kingburguer.compose.signup

import java.lang.reflect.Field

data class FieldState(
    var field: String = "",
    var error: String? = null
)
data class FormState(
    var name: FieldState = FieldState(),
    var email: FieldState = FieldState(),
    var password: FieldState = FieldState(),
    var confirmPassword: FieldState = FieldState()
)
