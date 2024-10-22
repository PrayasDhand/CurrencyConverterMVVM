package com.example.currencyconvertormvvm.ui.presentation.main_screen.Viewmodel

import android.icu.text.DecimalFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconvertormvvm.domain.model.Resource
import com.example.currencyconvertormvvm.domain.repository.CurrencyRepository
import com.example.currencyconvertormvvm.ui.presentation.main_screen.MainScreenEvent
import com.example.currencyconvertormvvm.ui.presentation.main_screen.MainScreenState
import com.example.currencyconvertormvvm.ui.presentation.main_screen.SelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repository: CurrencyRepository ) : ViewModel() {

    var state by mutableStateOf(MainScreenState())

    init {
        getCurrencyRatesList()
    }

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.BottomSheetItemClicked -> {
                when (state.selectionState) { // Check selectionState
                    SelectionState.FROM -> {
                        state = state.copy(fromCurrencyCode = event.value)
                    }
                    SelectionState.TO -> {
                        state = state.copy(toCurrencyCode = event.value)
                    }
                }
                updateCurrencyValue("")
            }
            MainScreenEvent.FromCurrencySelect -> {
                state = state.copy(selectionState = SelectionState.FROM)
            }
            is MainScreenEvent.NumberButtonClicked -> {
                state = updateCurrencyValue(value = event.value) // Update state with result of updateCurrencyValue
            }
            MainScreenEvent.SwapIconClicked -> {
                state = state.copy(
                    fromCurrencyCode = state.toCurrencyCode,
                    fromCurrencyValue = state.toCurrencyValue,
                    toCurrencyCode = state.fromCurrencyCode,
                    toCurrencyValue = state.fromCurrencyValue
                )
            }
            MainScreenEvent.ToCurrencySelect -> {
                state = state.copy(selectionState = SelectionState.TO)
            }
        }
    }

    private fun updateCurrencyValue(value: String): MainScreenState {
        val currentCurrencyValue = when (state.selectionState) {
            SelectionState.FROM -> state.fromCurrencyValue
            SelectionState.TO -> state.toCurrencyValue
        }

        val fromCurrencyRate = state.currencyRates[state.fromCurrencyCode]?.rate ?: 0.0
        val toCurrencyRate = state.currencyRates[state.toCurrencyCode]?.rate ?: 0.0

        val updatedCurrencyValue = when (value) {
            "C" -> "0.00"
            else -> if (currentCurrencyValue == "0.00") value else currentCurrencyValue + value
        }

        val numberFormat = DecimalFormat("#.00")

        return when (state.selectionState) {
            SelectionState.FROM -> {
                val fromValue = updatedCurrencyValue.toDoubleOrNull() ?: 0.0
                val toValue = fromValue / fromCurrencyRate * toCurrencyRate
                state.copy(
                    fromCurrencyValue = updatedCurrencyValue,
                    toCurrencyValue = numberFormat.format(toValue)
                )
            }
            SelectionState.TO -> {
                val toValue = updatedCurrencyValue.toDoubleOrNull() ?: 0.0
                val fromValue = toValue / toCurrencyRate * fromCurrencyRate
                state.copy(
                    toCurrencyValue = updatedCurrencyValue,
                    fromCurrencyValue = numberFormat.format(fromValue)
                )
            }
        }
    }

    private fun getCurrencyRatesList() {
        viewModelScope.launch {
            repository.getCurrencyRatesList().collectLatest { result ->
                state = when (result) {
                    is Resource.Error -> {
                        state.copy(
                            currencyRates = result.data?.associateBy { it.code } ?: emptyMap(),
                            error = result.message
                        )
                    }
                    is Resource.Success -> {
                        state.copy(
                            currencyRates = result.data?.associateBy { it.code } ?: emptyMap(),
                            error = null
                        )
                    }

                    Resource.Loading -> TODO()
                }
            }
        }
    }


}