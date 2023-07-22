package com.ofamosoron.dividimos.ui.composables.check

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.ui.composables.admob.BannerAd
import com.ofamosoron.dividimos.ui.composables.extraFee.ExtraFee
import com.ofamosoron.dividimos.ui.composables.extraFee.FeeType
import com.ofamosoron.dividimos.ui.util.EmptyScreen
import com.ofamosoron.dividimos.util.Constants.PERCENT_DIVISOR
import com.ofamosoron.dividimos.util.formatMoney

@SuppressWarnings("LongMethod")
@Composable
fun CheckDialog(
    size: Int = 500,
    guestId: String,
    onDismiss: () -> Unit,
    viewModel: CheckViewModel = hiltViewModel(),
) {
    val state = viewModel.checkState.collectAsState()
    viewModel.getState(guestId = guestId)

    Dialog(
        onDismissRequest = { onDismiss },
        properties = DialogProperties(dismissOnBackPress = false),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .size(size.dp),
        ) {
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "close",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                //Guest name
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start
                ) {
                    /*
                    * TODO
                    *  All this calculations should be done at the viewModel
                    * */
                    val itemsTotal = state.value.checks.sumOf { it.total.cents }
                    val serviceFee = state.value.serviceFee
                    val couvertFee = state.value.couvertFee
                    val total =
                        (itemsTotal.times(serviceFee)).div(PERCENT_DIVISOR) + itemsTotal + couvertFee
                            .times(PERCENT_DIVISOR)
                    Text(
                        text = state.value.guest.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(
                        text = total.formatMoney(),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
                Spacer(modifier = Modifier.padding(6.dp))
                Divider()
                BannerAd()
                Spacer(modifier = Modifier.padding(6.dp))

                if (state.value.checks.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (state.value.serviceFee > 0F) {
                            ExtraFee(
                                serviceValue = state.value.serviceFee,
                                feeType = FeeType.Service
                            ) { Unit }
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        if (state.value.couvertFee > 0F) {
                            ExtraFee(
                                serviceValue = state.value.couvertFee,
                                feeType = FeeType.Couvert
                            ) { Unit }
                        }
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(items = state.value.checks) { item ->
                            Spacer(modifier = Modifier.padding(6.dp))
                            Text(
                                text = item.dishName,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = item.total.cents.formatMoney(),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.padding(6.dp))
                        }
                    }
                }
            }
        }
    }
}
