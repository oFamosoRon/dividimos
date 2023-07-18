package com.ofamosoron.dividimos.ui

import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.DishToGuests
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.models.Money
import com.ofamosoron.dividimos.ui.composables.home.DialogType
import com.ofamosoron.dividimos.util.toMoney

data class MainState(
    val dishes: List<Dish> = emptyList(),
    val guests: List<Guest> = emptyList(),
    val dishesToGuests: List<DishToGuests> = emptyList(),
    val openDialog: DialogType = DialogType.CheckDialog(false, ""),
    val showAlert: Boolean = false
) {
    fun total(): Money =
        dishes.sumOf { it.price.cents.times(it.qnt) }.toMoney()
}
