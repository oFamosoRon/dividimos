package com.ofamosoron.dividimos.ui.composables.dishes

data class DishDialogState(
    val dishName: String = "",
    val dishPrice: String = "",
    val dishQuantity: Int = 1,
    val dishNameError: String? = null,
    val dishPriceError: String? = null,
    val dismiss: Boolean = false,
)