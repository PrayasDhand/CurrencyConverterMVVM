package com.example.currencyconvertormvvm.domain.repository

import com.example.currencyconvertormvvm.domain.model.CurrencyRate
import com.example.currencyconvertormvvm.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>>
}