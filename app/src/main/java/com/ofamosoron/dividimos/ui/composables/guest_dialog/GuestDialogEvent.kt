package com.ofamosoron.dividimos.ui.composables.guest_dialog

sealed class GuestDialogEvent {
    object AddNewGuest : GuestDialogEvent()
    data class OnNameChanged(val name: String) : GuestDialogEvent()
}
