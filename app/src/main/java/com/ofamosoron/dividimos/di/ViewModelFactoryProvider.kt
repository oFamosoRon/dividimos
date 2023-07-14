package com.ofamosoron.dividimos.di

import com.ofamosoron.dividimos.ui.composables.edit_dish.EditDishViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun editDishViewModelFactory(): EditDishViewModel.Factory
}
