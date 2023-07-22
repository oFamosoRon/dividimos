package com.ofamosoron.dividimos.ui.composables.extraFee

import androidx.lifecycle.ViewModel
import com.ofamosoron.dividimos.domain.usecase.SharedPreferencesUseCase
import com.ofamosoron.dividimos.util.SharedPreferencesHelper.COUVERT_FEE
import com.ofamosoron.dividimos.util.SharedPreferencesHelper.IS_COUVERT_INDIVIDUAL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CouvertFeeViewModel @Inject constructor(
    private val sharedPreferencesUseCase: SharedPreferencesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ExtraFeeState())
    val state = _state.asStateFlow()

    fun onEvent(event: ExtraFeeEvent) {
        when (event) {
            is ExtraFeeEvent.AddFee -> {
                sharedPreferencesUseCase.write(COUVERT_FEE, _state.value.serviceFee)
                sharedPreferencesUseCase.write(IS_COUVERT_INDIVIDUAL, _state.value.isIndividual)
            }
            is ExtraFeeEvent.UpdateValue -> {
                _state.value = _state.value.copy(serviceFee = event.feeValue)
            }
            ExtraFeeEvent.OnSwitch -> {
                val switch = _state.value.isIndividual
                _state.value = _state.value.copy(isIndividual = !switch)
            }
        }
    }
}