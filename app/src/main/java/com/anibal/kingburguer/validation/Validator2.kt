package com.anibal.kingburguer.validation

import TextString

interface Validator2 {
    fun validate (pattern: String, current: String, result: String): TextString?
}
