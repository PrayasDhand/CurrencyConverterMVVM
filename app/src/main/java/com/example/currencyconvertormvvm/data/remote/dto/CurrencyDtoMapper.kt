package com.example.currencyconvertormvvm.data.remote.dto

import com.example.currencyconvertormvvm.domain.model.CurrencyRate

fun CurrencyDto.ToCurrencyRates(): List<CurrencyRate>{
   val currencyData = this.data
   return listOf(
       CurrencyRate("INR", "Indian Rupee", currencyData.INR),
       CurrencyRate("USD", "US Dollar", currencyData.USD),
       CurrencyRate("EUR", "Euro", currencyData.EUR),
       CurrencyRate("GBP", "British Pound", currencyData.GBP),
       CurrencyRate("JPY", "Japanese Yen", currencyData.JPY),
       CurrencyRate("AUD", "Australian Dollar", currencyData.AUD),
       CurrencyRate("CAD", "Canadian Dollar", currencyData.CAD),
       CurrencyRate("CHF", "Swiss Franc", currencyData.CHF),
       CurrencyRate("CNY", "Chinese Yuan", currencyData.CNY),
       CurrencyRate("HKD", "Hong Kong Dollar", currencyData.HKD),
       CurrencyRate("SGD", "Singapore Dollar", currencyData.SGD),
       CurrencyRate("SEK", "Swedish Krona", currencyData.SEK),
       CurrencyRate("NZD", "New Zealand Dollar", currencyData.NZD),
       CurrencyRate("BRL", "Brazilian Real", currencyData.BRL),
       CurrencyRate("ZAR", "South African Rand", currencyData.ZAR),
       CurrencyRate("DKK", "Danish Krone", currencyData.DKK),
       CurrencyRate("MXN", "Mexican Peso", currencyData.MXN),
       CurrencyRate("MYR", "Malaysian Ringgit", currencyData.MYR),
       CurrencyRate("NOK", "Norwegian Krone", currencyData.NOK),
       CurrencyRate("PHP", "Philippine Peso", currencyData.PHP),
       CurrencyRate("PLN", "Polish Zloty", currencyData.PLN),
       CurrencyRate("RON", "Romanian Leu", currencyData.RON),
       CurrencyRate("RUB", "Russian Ruble", currencyData.RUB),
       CurrencyRate("THB", "Thai Baht", currencyData.THB),
       CurrencyRate("TRY", "Turkish Lira", currencyData.TRY),
       CurrencyRate("HRK", "Croatian Kuna", currencyData.HRK),
       CurrencyRate("IDR", "Indonesian Rupiah", currencyData.IDR),
       CurrencyRate("ILS", "Israeli New Shekel", currencyData.ILS),
       CurrencyRate("KRW", "South Korean Won", currencyData.KRW),
       CurrencyRate("HUF", "Hungarian Forint", currencyData.HUF),
       CurrencyRate("CZK", "Czech Koruna", currencyData.CZK),






   )
}