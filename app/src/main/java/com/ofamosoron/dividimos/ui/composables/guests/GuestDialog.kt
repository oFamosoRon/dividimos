package com.ofamosoron.dividimos.ui.composables.guests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.domain.models.Guest
import java.util.*

// TODO make this file not a dialog
// TODO create animation for when its entered
@Composable
fun GuestDialog(
    onDismiss: () -> Unit,
    addNewGuest: (guest: Guest) -> Unit
) {
    var name by remember { mutableStateOf("") }

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
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text(stringResource(id = R.string.guest_dialog_name_placeholder)) },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primary),
                    onClick = {
                        createNewGuest(name = name)?.let {
                            addNewGuest(it)
                            onDismiss()
                        }
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
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

// TODO move this to viewModel
private fun createNewGuest(name: String): Guest? {
    if (name.isEmpty())
        return null

    return Guest(name = name, uuid = UUID.randomUUID().toString())
}