package com.ofamosoron.dividimos.ui.composables.check

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.ui.util.EmptyScreen
import com.ofamosoron.dividimos.util.formatMoney

@Composable
fun CheckDialog(
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
                .size(500.dp),
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
                    val total = state.value.checks.sumOf { it.total.cents }
                    Text(
                        text = state.value.guest.name,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(
                        text = total.formatMoney(),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.padding(6.dp))
                Divider()
                Spacer(modifier = Modifier.padding(6.dp))

                if (state.value.checks.isEmpty()) {
                    EmptyScreen(
                        msg = R.string.dishes_alert,
                        icon = R.drawable.ic_empty_check,
                        fontSize = 18.sp,
                        space = 16.dp
                    )
                } else {
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