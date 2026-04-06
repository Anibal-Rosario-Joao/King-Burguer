package com.anibal.kingburguer.validation

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.anibal.kingburguer.R


interface TextString {
    @Composable // Para Funcionar o @StringRes
    fun asString(): String
}

// String que vem do Resource String.xml
class ResourceString(@StringRes private val input: Int): TextString{
    @Composable
    override fun asString(): String {
        return stringResource(input)
    }

}

// String Purra
class RawString( private val input: String): TextString{
    @Composable
    override fun asString(): String {
        return input
    }

}
class EmailValidator {

    fun validate(email: String): TextString?{
        if (email.isBlank()) {
            return ResourceString(R.string.error_email_black)
           // return "O campo não pode ser vazio"
        }
        if (!isEmailValid(email)){
            //return ResourceString(R.string.error_email_invalid)
            return RawString("O campo invalido, verifique o e-mail")
        }
        return null
    }

    private fun isEmailValid(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}