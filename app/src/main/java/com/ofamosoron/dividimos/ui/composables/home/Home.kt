package com.ofamosoron.dividimos.ui.composables.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.ui.MainViewModel
import com.ofamosoron.dividimos.ui.composables.actionMenu.CloseTableDialog
import com.ofamosoron.dividimos.ui.composables.check.CheckDialog
import com.ofamosoron.dividimos.ui.composables.header.Header
import com.ofamosoron.dividimos.ui.dragAndDrop.LongPressDraggable
import com.ofamosoron.dividimos.ui.navigation.Route
import com.ofamosoron.dividimos.ui.util.EmptyScreen

private const val ALPHA: Float = 0.2F

@SuppressWarnings("LongMethod", "MaxLineLength")
@Composable
fun Home(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController,
) {

}

@Composable
fun ChooseDialog(
    dialogType: DialogType,
    action: (dialogType: HomeScreenEvent) -> Unit,
) {
    if (dialogType.isOpen) {
        when (dialogType) {
            is DialogType.CheckDialog -> CheckDialog(
                onDismiss = { action(HomeScreenEvent.CloseDialog(dialogType = dialogType)) },
                guestId = (dialogType as? DialogType.CheckDialog)?.guestId ?: ""
            )
            is DialogType.ClearTableDialog -> CloseTableDialog(
                onDismiss = { action(HomeScreenEvent.CloseDialog(dialogType = dialogType)) },
                onProceed = {
                    action(HomeScreenEvent.CloseDialog(dialogType = dialogType))
                    action(HomeScreenEvent.ClearDatabase)
                }
            )
        }
    }
}
