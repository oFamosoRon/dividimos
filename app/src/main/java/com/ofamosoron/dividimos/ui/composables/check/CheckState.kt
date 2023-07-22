package com.ofamosoron.dividimos.ui.composables.check

import com.ofamosoron.dividimos.domain.models.Check
import com.ofamosoron.dividimos.domain.models.Guest

data class CheckState(
    val guest: Guest = Guest(),
    val checks: List<Check> = emptyList(),
    val serviceFee: Float = 0F,
    val couvertFee: Float = 0F,
    val isIndividual: Boolean? = null
)
