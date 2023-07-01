package com.ofamosoron.dividimos.domain.delegate

import com.ofamosoron.dividimos.ui.composables.dialog.DialogType

interface DialogDelegate {
    fun openDialog(dialogType: DialogType): DialogType
    fun closeDialog(dialogType: DialogType): DialogType
}