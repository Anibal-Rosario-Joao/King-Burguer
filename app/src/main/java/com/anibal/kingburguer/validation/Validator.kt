package com.anibal.kingburguer.validation

import TextString

abstract class Validator {
    open fun validate (input: String): TextString?{
        return null
    }
    open fun validate (input1: String, input2: String): TextString?{
        return null
    }
    open fun validate (pattern: String, current: String, result: String):TextString?{
        return null
    }
}