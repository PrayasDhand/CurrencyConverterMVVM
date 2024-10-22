package com.example.currencyconvertormvvm.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyconvertormvvm.data.local.dao.CurrencyRateDao
import com.example.currencyconvertormvvm.data.local.entity.CurrencyRateEntity


@Database(
    entities = [CurrencyRateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CurrencyRateDatabase: RoomDatabase() {

    abstract val currencyRateDao: CurrencyRateDao

}