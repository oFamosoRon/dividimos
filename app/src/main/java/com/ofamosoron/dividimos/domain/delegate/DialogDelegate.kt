package com.ofamosoron.dividimos.domain.delegate

import com.ofamosoron.dividimos.ui.composables.home.DialogType

interface DialogDelegate {
    fun openDialog(dialogType: DialogType): DialogType
    fun closeDialog(dialogType: DialogType): DialogType
}
