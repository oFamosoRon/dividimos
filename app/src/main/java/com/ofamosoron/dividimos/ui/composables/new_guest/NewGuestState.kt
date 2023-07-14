package com.ofamosoron.dividimos.ui.composables.new_guest

import com.ofamosoron.dividimos.domain.models.Guest

data class NewGuestState(
    val guest: Guest = Guest(),
    val isCreated: Boolean = false
)
