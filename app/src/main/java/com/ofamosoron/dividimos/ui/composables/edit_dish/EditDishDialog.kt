package com.ofamosoron.dividimos.ui.composables.edit_dish

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ofamosoron.dividimos.ui.util.NumberPicker
import com.ofamosoron.dividimos.util.formatMoney

@Composable
fun EditDishDialog(
    dishUui: String,
    onDismiss: () -> Unit,
    viewModel: EditDishDialogViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    viewModel.getState(dishUui)

    Dialog(
        onDismissRequest = {
            onDismiss()
            viewModel.clearState()
        },
        properties = DialogProperties(dismissOnBackPress = false),
    ) {
        if (state.value.dismiss) {
            viewModel.clearState()
            onDismiss()
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .size(500.dp),
        ) {

            Row(
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                TextButton(onClick = {
                    viewModel.saveChanges()
                    onDismiss()
                }) {
                    Text(
                        text = "Salvar",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                TextButton(onClick = {
                    viewModel.clearState()
                    onDismiss()
                }) {
                    Text(
                        text = "Descartar",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = state.value.dish.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.padding(6.dp))
            Divider()
            Spacer(modifier = Modifier.padding(6.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Preço: ", color = MaterialTheme.colorScheme.onSurface)
                OutlinedTextField(
                    value = state.value.dish.price.cents.formatMoney(),
                    onValueChange = {
                        viewModel.onEvent(EditEvent.Price(price = it))
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Quantidade: ", color = MaterialTheme.colorScheme.onSurface)
                NumberPicker(
                    initialValue = state.value.dish.qnt,
                    onValueChange = {
                        viewModel.onEvent(EditEvent.Quantity(it))
                    }
                )
            }

            if (state.value.guests.isNotEmpty()) {
                Text(text = "Dividindo entre: ", color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.padding(4.dp))
                LazyColumn {
                    items(state.value.guests) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.secondary,
                                )
                                .padding(start = 8.dp)
                        ) {
                            Text(text = it.name, color = MaterialTheme.colorScheme.onSurface)
                            IconButton(onClick = {
                                viewModel.onEvent(EditEvent.RemoveGuest(guestUuid = it.uuid))
                            }) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = "close",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                }
            }
        }
    }
}