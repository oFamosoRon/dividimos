package com.ofamosoron.dividimos.domain.models

import kotlinx.datetime.Instant

data class Guest(
    val id: Int = 0,
    val uuid: String = "",
    val name: String = "",
    val email: String? = null,
    val phone: String? = null,
    val leftAt: Instant? = null,
    val arrivedAt: Instant? = null,
    val checkIds: List<String> = emptyList(),
)