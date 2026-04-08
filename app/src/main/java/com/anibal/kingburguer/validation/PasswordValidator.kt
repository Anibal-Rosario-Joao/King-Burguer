package com.anibal.kingburguer.validation

import TextString
import com.anibal.kingburguer.R
import com.anibal.kingburguer.textstring.ResourceString

class PasswordValidator: Validator() {
    override fun validate(password1: String, password2: String): TextString?{
        if (password1.isBlank()){
            return ResourceString(R.string.error_field_blank)
        }
        if (password1.length < 8){
            return ResourceString(R.string.error_password_size)
        }
        if (password2.isNotBlank() && password2 != password1){
            return ResourceString(R.string.error_password_confirm)
        }
        return null
    }
}
