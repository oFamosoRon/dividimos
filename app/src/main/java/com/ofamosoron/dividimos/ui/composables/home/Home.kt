package com.ofamosoron.dividimos.ui.composables.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ofamosoron.dividimos.ui.MainState
import com.ofamosoron.dividimos.ui.MainViewModel
import com.ofamosoron.dividimos.ui.composables.admob.BannerAd
import com.ofamosoron.dividimos.ui.composables.header.CustomTopBar
import com.ofamosoron.dividimos.ui.dragAndDrop.LongPressDraggable
import com.ofamosoron.dividimos.ui.navigation.Route
import com.ofamosoron.dividimos.ui.util.EmptyScreen

private const val DISHES_WIGHT = 3F
private const val GUESTS_WIGHT = 1F

@SuppressWarnings("LongMethod")
@Composable
fun HomeScreenV2(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.mainState.collectAsState()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Dividimos",
                orderAmount = state.value.total(),
                actionMenuClearOrderClick = {
                    viewModel.onEvent(HomeScreenEvent.OpenDialog(DialogType.ClearTableDialog()))
                },
                actionMenuAddServiceFeeClick = {
                    navController.navigate(route = Route.AddServiceFeeScreen.url)
                }
            ) { Unit }
        },
    ) { padding ->


        if (state.value.showAlert) {
            ToastMsg(viewModel)
        }

        Box(
            modifier = Modifier
                .padding(top = padding.calculateTopPadding())
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            ChooseDialog(
                dialogType = state.value.openDialog,
                action = { viewModel.onEvent(it) }
            )

            Column {
                BannerAd()
                if (state.value.dishes.isNotEmpty()) {
                    AddButtons(navController)
                }
                LongPressDraggable {
                    if (state.value.dishes.isEmpty()) {
                        EmptyScreen { navController.navigate(route = Route.NewDishScreen.url) }
                    } else {
                        HomeContent(state.value, viewModel, navController)
                    }
                }
            }
        }
    }
}

@Composable
private fun ToastMsg(viewModel: MainViewModel) {
    val context = LocalContext.current.applicationContext
    Toast.makeText(
        context,
        "Convidado já está dividindo esse item",
        Toast.LENGTH_SHORT
    ).show()
    viewModel.onEvent(HomeScreenEvent.ClearAlert)
}

@Composable
private fun HomeContent(
    state: MainState,
    viewModel: MainViewModel,
    navController: NavController
) {
    Row(modifier = Modifier.padding(top = 4.dp)) {
        Box(modifier = Modifier.weight(DISHES_WIGHT)) {
            DishesContainer(
                dishes = state.dishes,
                guests = state.dishesToGuests,
                onIncreaseClick = { dishUuid ->
                    viewModel.onEvent(
                        HomeScreenEvent.IncreaseDishQuantity(
                            dishUuid = dishUuid
                        )
                    )
                },
                onDecreaseClick = { dishUuid ->
                    viewModel.onEvent(
                        HomeScreenEvent.DecreaseDishQuantity(
                            dishUuid = dishUuid
                        )
                    )
                }, onDrop = { guestUuid, dishUuid ->
                    viewModel.onEvent(
                        HomeScreenEvent.OnDrop(
                            guestUuid = guestUuid,
                            dishUuid = dishUuid
                        )
                    )
                }, onEditClick = { dishUuid: String ->
                    navController.navigate(Route.EditDishScreen.withArgs(dishUuid))
                })
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Box(modifier = Modifier.weight(GUESTS_WIGHT)) {
            GuestsContainer(
                guests = state.guests,
                cardClick = {
                    viewModel.onEvent(
                        HomeScreenEvent.OpenDialog(
                            dialogType = DialogType.CheckDialog(guestId = it)
                        )
                    )
                }
            )
        }
    }
}

@Composable
private fun AddButtons(navController: NavController) {
    Row(modifier = Modifier.padding(top = 8.dp, start = 4.dp, end = 4.dp)) {
        AddButton(weight = DISHES_WIGHT, icon = Icons.Default.AddCircle) {
            navController.navigate(route = Route.NewDishScreen.url)
        }
        Spacer(modifier = Modifier.padding(2.dp))
        AddButton(weight = GUESTS_WIGHT, icon = Icons.Default.Person) {
            navController.navigate(route = Route.NewGuestScreen.url)
        }
    }
}

@Composable
fun RowScope.AddButton(
    weight: Float,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.primary)
            .weight(weight)
            .clickable { onClick() }
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(8.dp)
        )
    }
}
