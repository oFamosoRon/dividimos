package com.ofamosoron.dividimos.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

private const val ZERO = 0F
private const val HUNDRED = 100
private const val TWO = 2

fun Float.formatMoney(): String {
    val parsedValue = this.toBigDecimal().setScale(TWO, BigDecimal.ROUND_FLOOR)
        .divide(BigDecimal(HUNDRED), BigDecimal.ROUND_FLOOR)
    val decimalFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR")) as DecimalFormat
    return decimalFormat.format(parsedValue)
}

object Constants {
    val FLOAT_ZERO: Float
        get() = ZERO
}
