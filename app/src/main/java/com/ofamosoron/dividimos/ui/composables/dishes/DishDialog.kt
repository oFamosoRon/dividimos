package com.ofamosoron.dividimos.ui.composables.dishes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.ui.util.NumberPicker
import com.ofamosoron.dividimos.util.formatMoney
import com.ofamosoron.dividimos.util.toMoney

// TODO make this file not a dialog
// TODO create animation for when its entered
@Composable
fun DishDialog(
    onDismiss: () -> Unit,
    viewModel: DishDialogViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

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
                .size(380.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                OutlinedTextField(
                    value = state.value.dishName,
                    singleLine = true,
                    onValueChange = {
                        viewModel.onEvent(FormValidationEvent.NameChanged(name = it))
                    },
                    placeholder = { Text(stringResource(R.string.dish_dialog_name_placeholder)) },
                    isError = state.value.dishNameError != null,
                    modifier = Modifier.fillMaxWidth(),
                )
                if (!state.value.dishNameError.isNullOrBlank()) {
                    Text(
                        text = "${state.value.dishNameError}",
                        style = TextStyle(color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedTextField(
                    value = state.value.dishPrice.formatMoney(),
                    singleLine = true,
                    onValueChange = {
                        val newValue = it.toMoney().cents.toString()
                        viewModel.onEvent(FormValidationEvent.PriceChanged(price = newValue))
                    },
                    placeholder = { Text(stringResource(R.string.dish_dialog_price_label)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                    ),
                    isError = state.value.dishPriceError != null,
                    modifier = Modifier.fillMaxWidth(),
                )
                if (!state.value.dishPriceError.isNullOrBlank()) {
                    Text(
                        text = "${state.value.dishPriceError}",
                        style = TextStyle(color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                NumberPicker(
                    initialValue = state.value.dishQuantity,
                    minValue = 1,
                    maxValue = 100,
                    onValueChange = {
                        viewModel.onEvent(FormValidationEvent.QuantityChanged(quantity = it))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(100.dp))
                            .background(MaterialTheme.colorScheme.primary),
                        onClick = {
                            viewModel.onEvent(
                                FormValidationEvent.SubmitButtonClicked(
                                    name = state.value.dishName,
                                    price = state.value.dishPrice,
                                    qnt = state.value.dishQuantity
                                )
                            )
                        }
                    ) {
                        Text(
                            stringResource(
                                id = R.string.dish_dialog_add_button_template,
                                "${state.value.dishQuantity}"
                            ),
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
            IconButton(onClick = {
                viewModel.clearState()
                onDismiss()
            }, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}