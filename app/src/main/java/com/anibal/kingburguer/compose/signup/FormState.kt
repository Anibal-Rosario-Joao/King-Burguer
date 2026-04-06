package com.anibal.kingburguer.compose.signup

import com.anibal.kingburguer.validation.TextString
import java.lang.reflect.Field

data class FieldState(
    var field: String = "",
    var error: TextString? = null
)
data class FormState(
    var name: FieldState = FieldState(),
    var email: FieldState = FieldState(),
    var password: FieldState = FieldState(),
    var confirmPassword: FieldState = FieldState(),
    var document: FieldState = FieldState(),
    var birthday: FieldState = FieldState()
)
