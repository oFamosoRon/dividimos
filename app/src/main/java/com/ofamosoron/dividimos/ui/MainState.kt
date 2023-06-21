package com.ofamosoron.dividimos.ui

import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.Guest

data class MainState(
    val dishes: List<Dish> = emptyList(),
    val guests: List<Guest> = emptyList(),
    val openDialog: MainViewModel.DialogType = MainViewModel.DialogType.DishDialog(false)
)