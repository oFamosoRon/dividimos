package com.ofamosoron.dividimos.ui.composables.extraFee

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ofamosoron.dividimos.ui.composables.header.CustomTopBar
import com.ofamosoron.dividimos.ui.navigation.Route
import com.ofamosoron.dividimos.ui.theme.DividimosTheme

@Composable
fun CouvertFeeScreen(
    viewModel: CouvertFeeViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Couvert Artistico",
                actionMenuClearOrderClick = { /*TODO*/ },
                actionMenuAddServiceFeeClick = { /*TODO*/ },
                actionMenuAddCouvertClick = { /*TODO*/ }
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        navigateBack(navController = navController)
                    }
                )
            }
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        ) {
            FeeSwitch(
                isSelected = state.value.isIndividual,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                viewModel.onEvent(ExtraFeeEvent.OnSwitch)
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.value.fee.toString(),
                    onValueChange = { newValue ->
                        viewModel.onEvent(ExtraFeeEvent.UpdateValue(newValue.toFloat()))
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                    ),
                    maxLines = 1
                )

                IconButton(
                    onClick = {
                        viewModel.onEvent(ExtraFeeEvent.AddFee)
                        navController.navigate(route = Route.HomeScreen.url) {
                            popUpTo(Route.AddCouvertFeeScreen.url) {
                                this.inclusive = true
                            }
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun FeeSwitch(isSelected: Boolean, modifier: Modifier = Modifier, onSwitch: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(100.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        FeeSwitchItem(text = "POR PESSOA", isSelected = isSelected) {
            onSwitch()
        }
        Spacer(modifier = Modifier.padding(8.dp))
        FeeSwitchItem(text = "POR MESA", isSelected = !isSelected) {
            onSwitch()
        }
    }
}

@Composable
private fun FeeSwitchItem(isSelected: Boolean, text: String, onClick: () -> Unit) {
    val bgColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.inversePrimary
    }

    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onPrimaryContainer
    }

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(bgColor)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = textColor
        )
    }
}

private fun navigateBack(navController: NavController) {
    navController.navigate(route = Route.HomeScreen.url) {
        popUpTo(Route.AddServiceFeeScreen.url) {
            this.inclusive = true
        }
    }
}

@Preview
@Composable
fun PreviewCouvertScreen() {
    DividimosTheme {
        CouvertFeeScreen(navController = NavController(LocalContext.current))
    }
}
