package com.ofamosoron.dividimos.ui.composables.newGuest

import com.ofamosoron.dividimos.domain.models.Guest

data class NewGuestState(
    val guest: Guest = Guest(),
    val isCreated: Boolean = false
)
