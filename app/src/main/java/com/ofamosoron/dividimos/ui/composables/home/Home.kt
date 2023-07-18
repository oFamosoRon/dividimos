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
import com.ofamosoron.dividimos.ui.MainViewModel
import com.ofamosoron.dividimos.ui.composables.admob.BannerAd
import com.ofamosoron.dividimos.ui.composables.header.CustomTopBar
import com.ofamosoron.dividimos.ui.dragAndDrop.LongPressDraggable
import com.ofamosoron.dividimos.ui.navigation.Route
import com.ofamosoron.dividimos.ui.util.EmptyScreen

private const val DISHES_WIGHT = 3F
private const val DISHES_BUTTON_WIGHT = 3.1F
private const val GUESTS_WIGHT = 1F
private const val GUESTS_BUTTON_WIGHT = 0.9F

@SuppressWarnings("LongMethod")
@Composable
fun HomeScreenV2(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {
    Scaffold(
        topBar = { CustomTopBar(title = "Dividimos", navigationIcon = {}) },
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

            Column {
                BannerAd()
                Row(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)) {
                    AddButton(weight = DISHES_BUTTON_WIGHT, icon = Icons.Default.AddCircle) {
                        navController.navigate(route = Route.NewDishScreen.url)
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    AddButton(weight = GUESTS_BUTTON_WIGHT, icon = Icons.Default.Person) {
                        navController.navigate(route = Route.NewGuestScreen.url)
                    }
                }
                LongPressDraggable {
                    if (state.value.dishes.isEmpty()) {
                        EmptyScreen { navController.navigate(route = Route.NewDishScreen.url) }
                    } else {
                        Row(modifier = Modifier.padding(top = 4.dp)) {
                            Box(modifier = Modifier.weight(DISHES_WIGHT)) {
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
                                        navController.navigate(
                                            Route.EditDishScreen.withArgs(
                                                dishUuid
                                            )
                                        )
                                    })
                            }
                            Spacer(modifier = Modifier.padding(4.dp))
                            Box(modifier = Modifier.weight(GUESTS_WIGHT)) {
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
            .clip(RoundedCornerShape(5.dp))
            .weight(weight)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable { onClick() }
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(8.dp)
        )
    }
}
