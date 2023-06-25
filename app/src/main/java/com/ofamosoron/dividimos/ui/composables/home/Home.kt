package com.ofamosoron.dividimos.ui.composables.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ofamosoron.dividimos.ui.MainViewModel
import com.ofamosoron.dividimos.ui.composables.check.CheckDialog
import com.ofamosoron.dividimos.ui.composables.dishes.DishDialog
import com.ofamosoron.dividimos.ui.composables.edit_dish.EditDishDialog
import com.ofamosoron.dividimos.ui.composables.guests.GuestDialog
import com.ofamosoron.dividimos.ui.composables.header.Header
import com.ofamosoron.dividimos.ui.drag_and_drop.*
import com.ofamosoron.dividimos.ui.util.EmptyScreen
import com.ofamosoron.dividimos.R

@Composable
fun Home(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.mainState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        val dialogType = state.value.openDialog
        if (dialogType.isOpen) {
            when (dialogType) {
                is MainViewModel.DialogType.DishDialog -> {
                    DishDialog(onDismiss = { viewModel.dismissDialog() })
                }
                is MainViewModel.DialogType.GuestDialog -> {
                    GuestDialog(
                        onDismiss = { viewModel.dismissDialog() },
                        addNewGuest = { guest ->
                            viewModel.addNewGuest(guest)
                        }
                    )
                }
                is MainViewModel.DialogType.CheckDialog -> CheckDialog(
                    onDismiss = { viewModel.dismissDialog() },
                    guestId = (dialogType as? MainViewModel.DialogType.CheckDialog)?.guestId ?: ""
                )
                is MainViewModel.DialogType.EditDishDialog -> EditDishDialog(
                    onDismiss = { viewModel.dismissDialog() },
                    dishUui = (dialogType as? MainViewModel.DialogType.EditDishDialog)?.dishUuid ?: ""
                )
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Header(
                addNewDish = { viewModel.openDialog(MainViewModel.DialogType.DishDialog()) },
                addNewGuest = { viewModel.openDialog(MainViewModel.DialogType.GuestDialog()) },
                actionMenuOptionOneClick = { viewModel.clearDatabase() },
                actionMenuOptionTwoClick = { },
                total = state.value.dishes.sumOf { it.price.cents * it.qnt }
            )

            LongPressDraggable(
                modifier = Modifier.fillMaxSize()
            ) {
                if (state.value.dishes.isEmpty()) {
                    EmptyScreen(msg = R.string.dishes_alert, icon = R.drawable.ic_empty_table)
                } else {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .height(420.dp)
                    ) {
                        DishesContainer(state.value.dishes, onClick = { dishId ->
                            viewModel.dishPlusOne(dishId)
                        }, onDrop = { guestUuid, dishUuid ->
                            viewModel.addGuestToDish(guestUuid = guestUuid, dishUuid = dishUuid)
                        }, onLongPress = { dishUuid: String ->
                            viewModel.openDialog(
                                MainViewModel.DialogType.EditDishDialog(dishUuid = dishUuid)
                            )
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
                            viewModel.openDialog(
                                MainViewModel.DialogType.CheckDialog(guestId = it)
                            )
                        }
                    )
                }
            }
        }
    }
}