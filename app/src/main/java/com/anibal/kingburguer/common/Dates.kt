package com.anibal.kingburguer.common

import java.text.DateFormat
import java.util.Date
import java.util.Locale

fun Date?.formatted(): String{
    if (this == null) return ""
    val df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
    return df.format(this)
}