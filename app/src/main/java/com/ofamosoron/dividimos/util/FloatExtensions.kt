package com.ofamosoron.dividimos.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Float.formatMoney(): String {
    val parsedValue = this.toBigDecimal().setScale(2, BigDecimal.ROUND_FLOOR).divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
    val decimalFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR")) as DecimalFormat
    return decimalFormat.format(parsedValue)
}