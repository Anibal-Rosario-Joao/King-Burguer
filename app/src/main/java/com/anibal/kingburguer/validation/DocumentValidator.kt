
package com.anibal.kingburguer.validation

import TextString
import com.anibal.kingburguer.R
import com.anibal.kingburguer.textstring.ResourceString

class DocumentValidator: Validator2 {
    override fun validate(pattern: String, current: String, result: String): TextString? {
        if (result.isBlank()){
            return ResourceString(R.string.error_field_blank)
        }
        if (result.length != pattern.length){
            return ResourceString(R.string.document_invalid)
        }
        return null
    }
}