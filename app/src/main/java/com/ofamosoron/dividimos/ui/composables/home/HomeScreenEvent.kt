package com.ofamosoron.dividimos.ui.composables.home

sealed class HomeScreenEvent {
    object ClearAlert: HomeScreenEvent()
    object ClearDatabase : HomeScreenEvent()
    data class OpenDialog(val dialogType: DialogType) : HomeScreenEvent()
    data class CloseDialog(val dialogType: DialogType) : HomeScreenEvent()
    data class IncreaseDishQuantity(val dishUuid: String) : HomeScreenEvent()
    data class DecreaseDishQuantity(val dishUuid: String) : HomeScreenEvent()
    data class OnDrop(val guestUuid: String, val dishUuid: String) : HomeScreenEvent()
}
