package com.ofamosoron.dividimos.util

import com.ofamosoron.dividimos.domain.models.Money

fun Long.toMoney(): Money = Money(cents = this)
fun Long.formatMoney(): String = this.toString().formatMoney()
