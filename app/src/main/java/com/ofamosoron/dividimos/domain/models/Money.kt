package com.ofamosoron.dividimos.domain.models

data class Money(
    val cents: Long = 0,
    val currency: String = "R$",
    val precision: Int = 100
)
