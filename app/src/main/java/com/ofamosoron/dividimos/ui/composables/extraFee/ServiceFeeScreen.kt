package com.ofamosoron.dividimos.ui.composables.extraFee

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ofamosoron.dividimos.domain.models.Money
import com.ofamosoron.dividimos.ui.composables.header.CustomTopBar
import com.ofamosoron.dividimos.ui.navigation.Route
import com.ofamosoron.dividimos.ui.theme.DividimosTheme
import com.ofamosoron.dividimos.util.Constants.SERVICE_FEE_10
import com.ofamosoron.dividimos.util.Constants.SERVICE_FEE_11
import com.ofamosoron.dividimos.util.Constants.SERVICE_FEE_15
import com.ofamosoron.dividimos.util.formatMoney
import com.ofamosoron.dividimos.util.toMoney

@SuppressWarnings("LongMethod")
@Composable
fun ServiceFeeScreen(
    navController: NavController,
    viewModel: ExtraFeeViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Taxa de Serviço",
                showActionMenu = false,
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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        ) {
            Text(text = "Valores Padrao")
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ExtraFee(serviceValue = 10F, feeType = FeeType.Service) {
                    viewModel.onEvent(ExtraFeeEvent.UpdateValue(SERVICE_FEE_10))
                    viewModel.onEvent(ExtraFeeEvent.AddFee)
                    navigateBack(navController = navController)

                }
                ExtraFee(serviceValue = 11F, feeType = FeeType.Service) {
                    viewModel.onEvent(ExtraFeeEvent.UpdateValue(SERVICE_FEE_11))
                    viewModel.onEvent(ExtraFeeEvent.AddFee)
                    navigateBack(navController = navController)
                }
                ExtraFee(serviceValue = 15F, feeType = FeeType.Service) {
                    viewModel.onEvent(ExtraFeeEvent.UpdateValue(SERVICE_FEE_15))
                    viewModel.onEvent(ExtraFeeEvent.AddFee)
                    navigateBack(navController = navController)
                }
            }
            Text(text = "Valor customziado")
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                OutlinedTextField(
                    value = state.value.serviceFee.toString(),
                    onValueChange = { newValue ->
                        viewModel.onEvent(ExtraFeeEvent.UpdateValue(feeValue = newValue.toFloat()))
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                    ),
                    singleLine = true,
                    modifier = Modifier.weight(2F),
                )

                Button(onClick = {
                    viewModel.onEvent(ExtraFeeEvent.AddFee)
                    navigateBack(navController)
                }, modifier = Modifier.weight(1F)) {
                    Text(text = "Adicionar")
                }
            }
        }

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
fun PreviewServiceFeeScreen() {
    DividimosTheme {
        ServiceFeeScreen(navController = NavController(LocalContext.current))
    }
}
