package com.ofamosoron.dividimos.ui.composables.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.ui.composables.actionMenu.ActionMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    navigationIcon: @Composable () -> Unit,
    onClickAction1: (() -> Unit)? = null,
    onClickAction2: (() -> Unit)? = null,
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
                Text(
                    text = "R$ 10,00",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                onClickAction1?.let {
                    IconButton(onClick = { it() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_dish),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = "new dish"
                        )
                    }
                }

                onClickAction2?.let {
                    IconButton(onClick = { it() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_guest),
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = "new guest"
                        )
                    }
                }

                ActionMenu(
                    onOptionOneClick = { /*TODO*/ },
                )
            }
        }
    )
}
