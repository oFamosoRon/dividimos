package com.ofamosoron.dividimos.data.delegate

import com.ofamosoron.dividimos.domain.delegate.DialogDelegate
import com.ofamosoron.dividimos.ui.composables.dialog.DialogType

class DialogDelegateImpl : DialogDelegate {
    override fun openDialog(dialogType: DialogType): DialogType {
        return when (dialogType) {
            is DialogType.DishDialog -> {
                dialogType.copy(isOpen = true)
            }

            is DialogType.GuestDialog -> {
                dialogType.copy(isOpen = true)
            }

            is DialogType.CheckDialog -> {
                dialogType.copy(isOpen = true)
            }

            is DialogType.EditDishDialog -> {
                dialogType.copy(isOpen = true)
            }

            is DialogType.ClearTableDialog -> {
                dialogType.copy(isOpen = true)
            }
        }
    }

    override fun closeDialog(dialogType: DialogType): DialogType {
        return when (dialogType) {
            is DialogType.DishDialog -> {
                DialogType.DishDialog(false)
            }

            is DialogType.GuestDialog -> {
                DialogType.GuestDialog(false)
            }

            is DialogType.CheckDialog -> {
                DialogType.CheckDialog(false, "")
            }

            is DialogType.EditDishDialog -> {
                DialogType.EditDishDialog(false, "")
            }

            is DialogType.ClearTableDialog -> {
                DialogType.ClearTableDialog(false)
            }
        }
    }


}