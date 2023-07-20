package com.ofamosoron.dividimos.ui.composables.extraFee

import androidx.lifecycle.ViewModel
import com.ofamosoron.dividimos.domain.usecase.SharedPreferencesUseCase
import com.ofamosoron.dividimos.util.SharedPreferencesHelper.SERVICE_FEE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExtraFeeViewModel @Inject constructor(
    private val sharedPreferencesUseCase: SharedPreferencesUseCase
) : ViewModel(), SharedPreferencesUseCase by sharedPreferencesUseCase {

    private val _state = MutableStateFlow(ExtraFeeState())
    val state = _state.asStateFlow()

    fun onEvent(event: ExtraFeeEvent) {
        when (event) {
            is ExtraFeeEvent.AddFee -> {
                sharedPreferencesUseCase.write(SERVICE_FEE, _state.value.serviceFee)
            }
            is ExtraFeeEvent.UpdateValue -> {
                _state.value = _state.value.copy(serviceFee = event.feeValue)
            }
        }
    }
}
