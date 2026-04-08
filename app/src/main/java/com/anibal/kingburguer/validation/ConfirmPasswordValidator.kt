package com.anibal.kingburguer.validation


import TextString
import com.anibal.kingburguer.R
import com.anibal.kingburguer.textstring.ResourceString

class ConfirmPasswordValidator: Validator(){

     override fun validate(password1: String, password2: String): TextString? {
         if (password2.isBlank()){
             return ResourceString(R.string.error_field_blank)
         }
         if (password2 != password1){
             return ResourceString(R.string.error_password_confirm)
         }
         if (password1.isNotBlank() && password2 != password1){
             return ResourceString(R.string.error_password_confirm)
         }

        return null
    }
}

