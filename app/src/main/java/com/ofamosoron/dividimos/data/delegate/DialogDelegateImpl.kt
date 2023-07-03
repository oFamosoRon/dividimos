package com.ofamosoron.dividimos.data.delegate

import com.ofamosoron.dividimos.domain.delegate.DialogDelegate
import com.ofamosoron.dividimos.ui.composables.home.DialogType

class DialogDelegateImpl : DialogDelegate {
    override fun openDialog(dialogType: DialogType): DialogType {
        return when (dialogType) {
            is DialogType.CheckDialog -> {
                dialogType.copy(isOpen = true)
            }

            is DialogType.ClearTableDialog -> {
                dialogType.copy(isOpen = true)
            }
        }
    }

    override fun closeDialog(dialogType: DialogType): DialogType {
        return when (dialogType) {
            is DialogType.CheckDialog -> {
                DialogType.CheckDialog(false, "")
            }

            is DialogType.ClearTableDialog -> {
                DialogType.ClearTableDialog(false)
            }
        }
    }


}