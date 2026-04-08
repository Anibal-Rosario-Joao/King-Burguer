package com.anibal.kingburguer.textstring

import TextString
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

// String que vem do Resource String.xml
class ResourceString(@StringRes private val input: Int): TextString{
    @Composable
    override fun asString(): String {
        return stringResource(input)
    }

}