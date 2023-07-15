package com.ofamosoron.dividimos.util

import com.ofamosoron.dividimos.domain.models.Money
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

private const val REPLACE: String = "[R$,.\u00A0\nA-Za-z]"
private const val ZERO: Int = 0
private const val ONE: Int = 1
private const val TWO: Int = 2
private const val HUNDRED: Int = 100

fun String.toMoney(): Money =
    this.replace(REPLACE.toRegex(), "").toLong().toMoney()

fun String.formatMoney(): String {
    val validValue = this.ifBlank { "0" }

    val parsedString = BigDecimal(validValue)
        .setScale(TWO, BigDecimal.ROUND_FLOOR)
        .divide(BigDecimal(HUNDRED), BigDecimal.ROUND_FLOOR)

    val decimalFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR")) as DecimalFormat
    return decimalFormat.format(parsedString)
}

fun String.capitalizeFirst(): String {
    if (this.isEmpty()) {
        return this
    }
    return this.substring(ZERO, ONE).uppercase() + this.substring(ONE)
}
