package com.ofamosoron.dividimos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ofamosoron.dividimos.ui.composables.editDish.EditDishDialog
import com.ofamosoron.dividimos.ui.composables.extraFee.CouvertFeeScreen
import com.ofamosoron.dividimos.ui.composables.extraFee.ServiceFeeScreen
import com.ofamosoron.dividimos.ui.composables.home.Home
import com.ofamosoron.dividimos.ui.composables.newDish.NewDishScreen
import com.ofamosoron.dividimos.ui.composables.newGuest.NewGuestScreen
import com.ofamosoron.dividimos.ui.composables.splashScreen.SplashScreen
import com.ofamosoron.dividimos.ui.composables.tutorial.Tutorial
import com.ofamosoron.dividimos.ui.composables.tutorial.TutorialPageOne
import com.ofamosoron.dividimos.ui.composables.tutorial.TutorialPageThree
import com.ofamosoron.dividimos.ui.composables.tutorial.TutorialPageTwo

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Route.SplashScreen.url) {
        composable(route = Route.SplashScreen.url) {
            SplashScreen(navController = navController)
        }

        composable(route = Route.AddServiceFeeScreen.url) {
            ServiceFeeScreen(navController = navController)
        }

        composable(route = Route.AddCouvertFeeScreen.url) {
            CouvertFeeScreen(navController = navController)
        }

        composable(route = Route.HomeScreen.url) {
            Home(navController = navController)
        }

        composable(route = Route.TutorialScreen.url) {
            Tutorial(
                list = listOf(
                    { TutorialPageOne() },
                    { TutorialPageTwo() },
                    { TutorialPageThree(navController = navController) }
                ))
        }

        composable(route = Route.NewGuestScreen.url) {
            NewGuestScreen(navController = navController)
        }

        composable(route = Route.NewDishScreen.url) {
            NewDishScreen(navController = navController)
        }

        composable(
            route = Route.EditDishScreen.url + "/{${RouteArgument.EDIT_SCREEN_ARGUMENT}}",
            arguments = listOf(
                navArgument(RouteArgument.EDIT_SCREEN_ARGUMENT) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { entry ->
            EditDishDialog(
                navController = navController,
                dishUui = entry.arguments?.getString(RouteArgument.EDIT_SCREEN_ARGUMENT) ?: ""
            )
        }
    }
}
