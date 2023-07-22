package com.ofamosoron.dividimos.ui.navigation

sealed class Route(val url: String) {
    object HomeScreen : Route(url = "route_home_screen")
    object SplashScreen : Route(url = "route_splash_screen")
    object NewDishScreen : Route(url = "route_new_dish_screen")
    object TutorialScreen : Route(url = "route_tutorial_screen")
    object NewGuestScreen : Route(url = "route_new_guest_screen")
    object EditDishScreen : Route(url = "route_edit_dish_screen")
    object AddServiceFeeScreen : Route(url = "route_add_service_fee_screen")
    object AddCouvertFeeScreen : Route(url = "route_add_couvert_fee_screen")

    fun withArgs(vararg args: String): String =
        buildString {
            append(url)
            args.forEach { arg ->
                append("/$arg")
            }
        }
}

object RouteArgument {
    const val EDIT_SCREEN_ARGUMENT = "EDIT_SCREEN_ARGUMENT"
}
