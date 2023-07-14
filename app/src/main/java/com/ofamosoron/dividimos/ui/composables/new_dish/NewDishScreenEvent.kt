package com.ofamosoron.dividimos.ui.composables.new_dish

sealed class NewDishScreenEvent {
    object ClearState: NewDishScreenEvent()
    data class NameChanged(val name: String) : NewDishScreenEvent()
    data class PriceChanged(val price: String) : NewDishScreenEvent()
    data class QuantityChanged(val quantity: Int) : NewDishScreenEvent()
    data class SubmitButtonClicked(val name: String, val price: String, val qnt: Int) :
        NewDishScreenEvent()
}

