
package com.anibal.kingburguer.validation

import TextString
import com.anibal.kingburguer.R
import com.anibal.kingburguer.textstring.ResourceString

class DocumentValidator: Validator() {
    val pattern = "###.###.###-##"
   lateinit var result: String
   lateinit var currentDocument: String
   lateinit var document: String

    override fun validate(input1:String, input2: String): TextString? {
        currentDocument = input1
        document = input2
        result = Mask(pattern, currentDocument, document)

        if (result.isBlank()){
            return ResourceString(R.string.error_field_blank)
        }
        if (result.length != pattern.length){
            return ResourceString(R.string.document_invalid)
        }
        return null
    }
}