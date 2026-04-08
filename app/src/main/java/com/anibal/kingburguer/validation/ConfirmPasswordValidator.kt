package com.anibal.kingburguer.validation


import TextString
import com.anibal.kingburguer.R
import com.anibal.kingburguer.textstring.ResourceString

class ConfirmPasswordValidator{

     fun validate(input1: String, input2: String): TextString? {
        val currentPassword = input1
        val  previewPassword = input2

        if (currentPassword.isBlank()){
            return ResourceString(R.string.error_field_blank)
        }

        if (previewPassword != currentPassword){
            return ResourceString(R.string.error_password_confirm)
        }

        return null
    }
}

