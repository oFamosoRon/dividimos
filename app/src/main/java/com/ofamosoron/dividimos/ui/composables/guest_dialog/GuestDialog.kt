package com.ofamosoron.dividimos.ui.composables.guest_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ofamosoron.dividimos.R

// TODO make this file not a dialog
// TODO create animation for when its entered
@Composable
fun GuestDialog(
    onDismiss: () -> Unit,
    viewModel: GuestDialogViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    Dialog(
        onDismissRequest = { onDismiss },
        properties = DialogProperties(dismissOnBackPress = false),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .size(300.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = state.value.guest.name,
                    onValueChange = {
                        viewModel.onEvent(GuestDialogEvent.OnNameChanged(name = it))
                    },
                    placeholder = { Text(stringResource(id = R.string.guest_dialog_name_placeholder)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primary),
                    onClick = {
                        viewModel.onEvent(GuestDialogEvent.AddNewGuest)
                        onDismiss()
                    }
                ) {
                    Text(
                        stringResource(id = R.string.guest_dialog_add_button_label),
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
            IconButton(onClick = onDismiss, modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "close",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}