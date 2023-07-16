package com.ofamosoron.dividimos.ui.composables.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.ofamosoron.dividimos.ui.composables.header.CustomTopBar
import com.ofamosoron.dividimos.ui.dragAndDrop.LongPressDraggable
import com.ofamosoron.dividimos.ui.navigation.Route
import com.ofamosoron.dividimos.ui.util.EmptyScreen

private const val ALPHA = 0.2F

@SuppressWarnings("LongMethod")
@Composable
fun HomeScreenV2(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {
    Scaffold(
        topBar = {
            CustomTopBar(title = "Dividimos", navigationIcon = {})
        },
    ) { padding ->
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
                .padding(top = padding.calculateTopPadding())
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            ChooseDialog(
                dialogType = state.value.openDialog,
                action = { viewModel.onEvent(it) }
            )

            Column(modifier = Modifier.fillMaxSize()) {
                LongPressDraggable(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (state.value.dishes.isEmpty()) {
                        EmptyScreen {
                            navController.navigate(route = Route.NewDishScreen.url)
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .height(420.dp)
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
                                .alpha(ALPHA)
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
}
