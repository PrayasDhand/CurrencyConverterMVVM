package com.example.currencyconvertormvvm.data.local.mapper

import com.example.currencyconvertormvvm.data.local.entity.CurrencyRateEntity
import com.example.currencyconvertormvvm.domain.model.CurrencyRate

fun CurrencyRateEntity.toCurrencyRate(): CurrencyRate{
    return CurrencyRate(
        code = code,
        name = name,
        rate = rate

    )
}

fun CurrencyRate.toCurrencyRateEntity(): CurrencyRateEntity{
    return CurrencyRateEntity(
        code = code,
        name = name,
        rate = rate

    )
}