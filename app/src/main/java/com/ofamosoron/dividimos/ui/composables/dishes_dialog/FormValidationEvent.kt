package com.ofamosoron.dividimos.ui.composables.dishes_dialog

sealed class FormValidationEvent {
    data class NameChanged(val name: String) : FormValidationEvent()
    data class PriceChanged(val price: String) : FormValidationEvent()
    data class QuantityChanged(val quantity: Int) : FormValidationEvent()
    data class SubmitButtonClicked(val name: String, val price: String, val qnt: Int) :
        FormValidationEvent()
}
