package com.anibal.kingburguer.validation

import TextString

interface Validator {
    fun validate (input: String): TextString?
}