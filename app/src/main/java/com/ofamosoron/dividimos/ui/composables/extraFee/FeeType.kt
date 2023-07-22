package com.ofamosoron.dividimos.ui.composables.extraFee

sealed class FeeType {
    object Service: FeeType()
    object Couvert: FeeType()
}
