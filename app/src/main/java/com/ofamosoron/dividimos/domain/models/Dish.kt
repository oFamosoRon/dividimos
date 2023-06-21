package com.ofamosoron.dividimos.domain.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Dish(
    val id: Int = 0,
    val qnt: Int = 0,
    val price: Money = Money(),
    val name: String = "",
    val uuid: String = "",
    val guests: List<String> = emptyList(),
    val createdAt: Instant = Clock.System.now(),
    val checkId: String = ""
)