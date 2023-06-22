package com.ofamosoron.dividimos.util

import com.ofamosoron.dividimos.domain.models.Money
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

private const val REPLACE: String = "[R$,.\u00A0\nA-Za-z]"

fun String.toMoney(): Money =
    this.replace(REPLACE.toRegex(), "").toLong().toMoney()

fun String.formatMoney(): String {
    val validValue = this.ifBlank { "0" }

    val parsedString = BigDecimal(validValue)
        .setScale(2, BigDecimal.ROUND_FLOOR)
        .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)

    val decimalFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR")) as DecimalFormat
    return decimalFormat.format(parsedString)
}

fun String.capitalizeFirst(): String {
    if (this.isEmpty()) {
        return this
    }
    return this.substring(0, 1).uppercase() + this.substring(1)
}