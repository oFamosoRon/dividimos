package com.ofamosoron.dividimos.ui.composables.home

sealed class DialogType(open val isOpen: Boolean) {
    data class ClearTableDialog(override val isOpen: Boolean = false) :
        DialogType(isOpen = isOpen)

    data class CheckDialog(override val isOpen: Boolean = false, val guestId: String) :
        DialogType(isOpen = isOpen)
}