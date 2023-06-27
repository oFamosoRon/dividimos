package com.ofamosoron.dividimos.util

import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.DishToGuests
import com.ofamosoron.dividimos.domain.models.Guest

fun dishToGuests(dish: Dish, guests: List<Guest>): DishToGuests =
    DishToGuests(
        dishUuid = dish.uuid,
        dishName = dish.name,
        guests = guests.filter { guest ->
            dish.guests.contains(guest.uuid)
        }
    )

