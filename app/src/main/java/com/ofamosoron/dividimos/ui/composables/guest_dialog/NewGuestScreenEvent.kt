package com.ofamosoron.dividimos.ui.composables.guest_dialog

sealed class NewGuestScreenEvent {
    object ClearState: NewGuestScreenEvent()
    object AddNewGuest : NewGuestScreenEvent()
    data class OnNameChanged(val name: String) : NewGuestScreenEvent()
}
