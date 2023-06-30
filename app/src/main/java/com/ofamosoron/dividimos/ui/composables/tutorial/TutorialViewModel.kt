package com.ofamosoron.dividimos.ui.composables.tutorial

import androidx.lifecycle.ViewModel
import com.ofamosoron.dividimos.domain.usecase.SharedPreferencesUseCase
import com.ofamosoron.dividimos.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val sharedPreferencesUseCase: SharedPreferencesUseCase
): ViewModel() {

   fun onFinishTutorial() {
       sharedPreferencesUseCase.write(key = SharedPreferencesHelper.TUTORIAL_STATUS, data = true)
   }
}