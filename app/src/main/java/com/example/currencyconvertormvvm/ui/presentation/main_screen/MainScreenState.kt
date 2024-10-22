package com.example.currencyconvertormvvm.ui.presentation.main_screen

import com.example.currencyconvertormvvm.domain.model.CurrencyRate

data class MainScreenState(
    val fromCurrencyCode: String = "INR",
    val toCurrencyCode: String = "USD",
    val fromCurrencyValue: String = "0.00",
    val toCurrencyValue: String = "0.00",
    val selectionState: SelectionState = SelectionState.FROM,
    val error: String? = null,
    val currencyRates: Map<String, CurrencyRate> = emptyMap(),

    )

enum class SelectionState{

    FROM,
    TO
}