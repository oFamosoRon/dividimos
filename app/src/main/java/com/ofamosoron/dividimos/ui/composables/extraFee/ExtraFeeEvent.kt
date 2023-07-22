package com.ofamosoron.dividimos.ui.composables.extraFee

sealed class ExtraFeeEvent {
    object AddFee: ExtraFeeEvent()
    data class UpdateValue(val feeValue: Float): ExtraFeeEvent()
    object OnSwitch: ExtraFeeEvent()
}
