package com.anibal.kingburguer.validation

fun Mask (pattern: String, currentDocument: String, newValue: String): String {
   val str = newValue.filter { it.isLetterOrDigit() }

    var result = ""
    var i = 0
    for (char in pattern){
        if (char != '#'){
            result += char

            if (currentDocument > newValue && result.length >= newValue.length){
                result = result.dropLast(1)
            }
            continue
        }

        if (i >= str.length) break
        result += str[i]
        i += 1
    }
    return result
}