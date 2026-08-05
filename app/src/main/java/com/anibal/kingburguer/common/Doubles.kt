package com.anibal.kingburguer.common

import java.text.NumberFormat
import java.util.Locale

fun Double.currency(): String{
    val formatter = NumberFormat.getCurrencyInstance(Locale("pt","Mz"))
    return formatter.format(this
    )
}