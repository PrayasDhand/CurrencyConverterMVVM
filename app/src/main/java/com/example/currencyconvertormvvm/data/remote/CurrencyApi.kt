package com.example.currencyconvertormvvm.data.remote

import com.example.currencyconvertormvvm.data.remote.dto.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("v1/latest")
    suspend fun getLatestRates( @Query("apikey") apiKey: String = API_KEY) : CurrencyDto

        companion object{
            const val BASE_URL = "https://api.freecurrencyapi.com/"
            const val API_KEY = "YOUR API KEY"

        }
    }


