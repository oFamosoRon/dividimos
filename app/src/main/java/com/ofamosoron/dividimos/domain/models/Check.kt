package com.ofamosoron.dividimos.domain.models

data class Check(
    val id: Int = 0,
    val total: Money = Money(),
    val uuid: String = "",
    val dishName: String = ""
)