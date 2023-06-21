package com.ofamosoron.dividimos.ui.composables.edit_dish

sealed class EditEvent {
    data class RemoveGuest(val guestUuid: String) : EditEvent()
    data class Quantity(val quantity: Int) : EditEvent()
    data class Price(val price: String) : EditEvent()
}