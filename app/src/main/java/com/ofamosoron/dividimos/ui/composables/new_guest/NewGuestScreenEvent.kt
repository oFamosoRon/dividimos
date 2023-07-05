package com.ofamosoron.dividimos.ui.composables.new_guest

sealed class NewGuestScreenEvent {
    object ClearState: NewGuestScreenEvent()
    object AddNewGuest : NewGuestScreenEvent()
    data class OnNameChanged(val name: String) : NewGuestScreenEvent()
}
