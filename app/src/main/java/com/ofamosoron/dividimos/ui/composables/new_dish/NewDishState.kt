package com.ofamosoron.dividimos.ui.composables.new_dish

data class NewDishState(
    val dishName: String = "",
    val dishPrice: String = "",
    val dishQuantity: Int = 1,
    val dishNameError: String? = null,
    val dishPriceError: String? = null,
    val isCreated: Boolean = false,
)