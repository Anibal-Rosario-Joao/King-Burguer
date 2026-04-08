package com.anibal.kingburguer.textstring

import TextString
import androidx.compose.runtime.Composable

class RawString( private val input: String): TextString{
    @Composable
    override fun asString(): String {
        return input
    }

}