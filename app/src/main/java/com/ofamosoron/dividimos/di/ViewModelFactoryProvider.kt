package com.ofamosoron.dividimos.di

import com.ofamosoron.dividimos.ui.composables.edit_dish.EditDishDialogViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun editDishViewModelFactory(): EditDishDialogViewModel.Factory
}