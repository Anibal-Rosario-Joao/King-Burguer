package com.anibal.kingburguer.validation

import TextString
import android.util.Patterns
import com.anibal.kingburguer.R
import com.anibal.kingburguer.textstring.RawString
import com.anibal.kingburguer.textstring.ResourceString

class EmailValidator: Validator() {


    override fun validate(input: String): TextString?{
        if (input.isBlank()) {
            return ResourceString(R.string.error_field_blank)
           // return "O campo não pode ser vazio"
        }
        if (!isEmailValid(input)){
            return ResourceString(R.string.error_email_invalid)
        }
        return null
    }

    private fun isEmailValid(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}