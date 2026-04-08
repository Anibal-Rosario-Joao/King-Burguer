package com.anibal.kingburguer.validation

import TextString
import com.anibal.kingburguer.R

import com.anibal.kingburguer.textstring.ResourceString

class NameValidator: Validator(){

     override fun validate(input: String): TextString? {
        if (input.isBlank()){
            return ResourceString(R.string.error_field_blank)
        }

        if (input.length < 3){
            return ResourceString(R.string.error_name_size)
        }
        return null
    }


}
