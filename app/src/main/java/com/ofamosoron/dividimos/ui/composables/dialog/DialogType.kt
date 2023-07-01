package com.ofamosoron.dividimos.ui.composables.dialog

sealed class DialogType(open val isOpen: Boolean) {
    data class DishDialog(override val isOpen: Boolean = false) : DialogType(isOpen = isOpen)
    data class GuestDialog(override val isOpen: Boolean = false) : DialogType(isOpen = isOpen)
    data class ClearTableDialog(override val isOpen: Boolean = false) :
        DialogType(isOpen = isOpen)

    data class CheckDialog(override val isOpen: Boolean = false, val guestId: String) :
        DialogType(isOpen = isOpen)

    data class EditDishDialog(override val isOpen: Boolean = false, val dishUuid: String) :
        DialogType(isOpen = isOpen)
}