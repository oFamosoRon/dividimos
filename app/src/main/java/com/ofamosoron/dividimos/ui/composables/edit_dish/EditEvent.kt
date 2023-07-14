package com.ofamosoron.dividimos.ui.composables.edit_dish

sealed class EditEvent {
    object ClearState: EditEvent()
    object Dismiss: EditEvent()
    object SaveChanges: EditEvent()
    data class RemoveGuest(val guestUuid: String) : EditEvent()
    data class Quantity(val quantity: Int) : EditEvent()
    data class Price(val price: String) : EditEvent()
}
