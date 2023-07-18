package com.ofamosoron.dividimos.ui.composables.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ofamosoron.dividimos.domain.models.Money
import com.ofamosoron.dividimos.ui.composables.actionMenu.ActionMenu
import com.ofamosoron.dividimos.util.formatMoney

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    orderAmount: Money? = null,
    showActionMenu: Boolean = true,
    actionMenuClearOrderClick: () -> Unit,
    actionMenuAddServiceFeeClick: () -> Unit,
    navigationIcon: @Composable () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            navigationIcon()
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.padding(4.dp))
                orderAmount?.let {
                    Text(
                        text = it.cents.formatMoney(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
        },
        actions = {
            if (showActionMenu) {
                ActionMenu(
                    onClearOrderClick = { actionMenuClearOrderClick() },
                    onAddServiceFeeClick = { actionMenuAddServiceFeeClick() }
                )
            }
        }
    )
}
