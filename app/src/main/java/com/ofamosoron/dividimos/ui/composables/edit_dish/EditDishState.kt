package com.ofamosoron.dividimos.ui.composables.edit_dish

import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.Guest

data class EditDishState(
    val dish: Dish = Dish(),
    val guests: List<Guest> = emptyList(),
    val removedGuests: List<Guest> = emptyList(),
    val dismiss: Boolean = false,
)