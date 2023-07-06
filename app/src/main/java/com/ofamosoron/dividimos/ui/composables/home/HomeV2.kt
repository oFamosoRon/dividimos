package com.ofamosoron.dividimos.ui.composables.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.ui.MainViewModel
import com.ofamosoron.dividimos.ui.composables.action_menu.ActionMenu
import com.ofamosoron.dividimos.ui.drag_and_drop.LongPressDraggable
import com.ofamosoron.dividimos.ui.navigation.Route
import com.ofamosoron.dividimos.ui.util.EmptyScreen

@Composable
fun HomeScreenV2(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {
    TODO()
    Scaffold(
        topBar = { CustomTopBar() },
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
                        EmptyScreen(msg = R.string.dishes_alert, icon = R.drawable.ic_empty_table)
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    onAddDishClick: () -> Unit,
    onAddGuestClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Dividimos",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "R$ 10,00",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_dish),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = "new dish"
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_guest),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = "new guest"
                    )
                }

                ActionMenu(
                    onOptionOneClick = { /*TODO*/ },
                    onOptionTwoClick = {  }
                )
            }
        }
    )
}