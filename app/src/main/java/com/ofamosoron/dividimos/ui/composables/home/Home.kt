package com.ofamosoron.dividimos.ui.composables.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.ui.MainViewModel
import com.ofamosoron.dividimos.ui.composables.action_menu.CloseTableDialog
import com.ofamosoron.dividimos.ui.composables.check.CheckDialog
import com.ofamosoron.dividimos.ui.composables.header.Header
import com.ofamosoron.dividimos.ui.drag_and_drop.LongPressDraggable
import com.ofamosoron.dividimos.ui.navigation.Route
import com.ofamosoron.dividimos.ui.util.EmptyScreen
import okhttp3.internal.checkDuration

@SuppressWarnings("LongMethod")
@Composable
fun Home(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.mainState.collectAsState()

    if (state.value.showAlert) {
        val context = LocalContext.current.applicationContext
        Toast.makeText(
            context,
            "Convidado já está dividindo esse item",
            Toast.LENGTH_SHORT
        ).show()
        viewModel.onEvent(HomeScreenEvent.ClearAlert)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        ChooseDialog(
            dialogType = state.value.openDialog,
            action = { viewModel.onEvent(it) }
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Header(
                addNewDish = { navController.navigate(Route.NewDishScreen.url) },
                addNewGuest = { navController.navigate(Route.NewGuestScreen.url) },
                actionMenuOptionOneClick = { viewModel.onEvent(HomeScreenEvent.OpenDialog(dialogType = DialogType.ClearTableDialog())) },
                actionMenuOptionTwoClick = { /* TODO */ },
                total = state.value.dishes.sumOf { it.price.cents * it.qnt }
            )

            LongPressDraggable(
                modifier = Modifier.fillMaxSize()
            ) {
                if (state.value.dishes.isEmpty()) {
                    EmptyScreen(msg = R.string.dishes_alert, icon = R.drawable.ic_empty_table)
                } else {
                    Box(
                        modifier = Modifier.align(Alignment.TopCenter).padding(bottom = 56.dp)
                    ) {
                        DishesContainer(
                            dishes = state.value.dishes,
                            guests = state.value.dishesToGuests,
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
                }

                if (state.value.guests.isEmpty()) {
                    Text(
                        text = stringResource(R.string.guest_alert),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .alpha(0.2F)
                            .align(Alignment.BottomCenter)
                            .padding(4.dp)
                    )
                } else {
                    GuestsContainer(
                        guests = state.value.guests,
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
    }
}

@Composable
fun ChooseDialog(
    dialogType: DialogType,
    action: (dialogType: HomeScreenEvent) -> Unit,
) {
    if (dialogType.isOpen) {
        when (dialogType) {
            is DialogType.CheckDialog -> CheckDialog(
                onDismiss = { action(HomeScreenEvent.CloseDialog(dialogType = dialogType)) },
                guestId = (dialogType as? DialogType.CheckDialog)?.guestId ?: ""
            )
            is DialogType.ClearTableDialog -> CloseTableDialog(
                onDismiss = { action(HomeScreenEvent.CloseDialog(dialogType = dialogType)) },
                onProceed = {
                    action(HomeScreenEvent.CloseDialog(dialogType = dialogType))
                    action(HomeScreenEvent.ClearDatabase)
                }
            )
        }
    }
}
