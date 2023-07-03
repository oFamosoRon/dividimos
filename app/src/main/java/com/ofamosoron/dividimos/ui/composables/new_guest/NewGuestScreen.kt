package com.ofamosoron.dividimos.ui.composables.new_guest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.ui.navigation.Route

@Composable
fun NewGuestScreen(
    viewModel: NewGuestViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.collectAsState()

    if (state.value.isCreated) {
        viewModel.onEvent(NewGuestScreenEvent.ClearState)
        navController.navigate(Route.HomeScreen.url) {
            popUpTo(Route.NewGuestScreen.url) {
                this.inclusive = true
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)

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
                    viewModel.onEvent(NewGuestScreenEvent.OnNameChanged(name = it))
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
                    viewModel.onEvent(NewGuestScreenEvent.AddNewGuest)
                }
            ) {
                Text(
                    stringResource(id = R.string.guest_dialog_add_button_label),
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}