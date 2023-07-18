package com.ofamosoron.dividimos.ui.composables.home

import androidx.compose.runtime.Composable
import com.ofamosoron.dividimos.ui.composables.actionMenu.CloseTableDialog
import com.ofamosoron.dividimos.ui.composables.check.CheckDialog

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
